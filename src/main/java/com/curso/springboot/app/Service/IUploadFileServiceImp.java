package com.curso.springboot.app.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class IUploadFileServiceImp implements IUploadFileService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource verFoto(String filename) throws MalformedURLException {
        Path pathFoto = getPath(filename);
        logger.info("pathFoto: "+ pathFoto);
        Resource resource=null;

        resource = new UrlResource(pathFoto.toUri()); //aca se carga la imagen
        if(!resource.exists() && !resource.isReadable()){
            throw new RuntimeException("Error, no se puede cargar la imagen: "+ pathFoto.toString());
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path rootPath = getPath(uniqueFileName);
        logger.info("absolutePath: "+rootPath); //path absoluto
        Files.copy(file.getInputStream(),rootPath);
        return uniqueFileName;
    }

    @Override
    public void delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();
        if(archivo.exists() && archivo.canRead()){
            archivo.delete();
        }
    }

    @Override
    public void deleteAll() throws IOException {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toAbsolutePath());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }

    public Path getPath(String filename){
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }
}

package com.curso.springboot.app.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadFileService {

    public Resource verFoto(String filename) throws MalformedURLException;
    public String copy(MultipartFile file) throws IOException;
    public void delete(String filename);
    public void deleteAll() throws IOException;                       /// deleteAll permite borrar tod0 el directorio uploads
    public void init() throws IOException;         /// permite crear el directorio uploads

}

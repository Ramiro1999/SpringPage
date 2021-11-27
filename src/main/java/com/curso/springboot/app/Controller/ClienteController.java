package com.curso.springboot.app.Controller;


import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Service.IClienteService;
import com.curso.springboot.app.Service.IUploadFileService;
import com.curso.springboot.app.paginator.PageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;


import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes("cliente") //guardamos el objeto del formulario dentro de la sesion
public class ClienteController {


    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("/listar-rest")
    public @ResponseBody List<Cliente> listarRest() {   //API REST, el listado de los clientes se va a almacenar en el cuerpo de la respuesta
        //de forma automatica al guardarse spring va a deducir que es un rest, puede ser json o xml

        return clienteService.findAll2();
    }



    @GetMapping({"/listar","/"})
    public String listar(@RequestParam(name = "page",defaultValue = "0") int page, Model model,
      @RequestParam(name = "format", defaultValue = "html") String format){
        Pageable pageRequest = PageRequest.of(page,4);
        if(format.equals("json") ){
            List<Cliente> clientes = clienteService.findAll2();
            model.addAttribute("clientes", clientes);

        }else {
            PageRender<Cliente> pageRender = new PageRender<>("/listar", clienteService.findAll(pageRequest));
            model.addAttribute("titulo", "Listado de clientes");
            model.addAttribute("clientes", clienteService.findAll(pageRequest));
            model.addAttribute("page", pageRender);
            model.addAttribute("clienteDNI", new Cliente());
        }
        return "listar";
    }


    @GetMapping("/form")
    public String crear(Model model){
        model.addAttribute("cliente",new Cliente());
        model.addAttribute("mensaje","Guardar cliente");
        model.addAttribute("titulo","Formulario de cliente");
        return "form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,RedirectAttributes flash){
        Optional<Cliente> cliente;
        if(id>0){
            cliente = clienteService.buscarCliente(id);
            if(cliente.isEmpty()){
                flash.addFlashAttribute("danger", "El cliente no existe!");
            }
        }else{
            flash.addFlashAttribute("danger", "El cliente no existe!");
            return "redirect:/listar";
        }
        String mensaje= (cliente.get().getId() != null)?"Editar cliente":"Guardar cliente";
        model.addAttribute("mensaje",mensaje);
        model.addAttribute("cliente",cliente);
        model.addAttribute("titulo","Editar cliente");
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id,RedirectAttributes flash){
        Optional<Cliente> cliente = clienteService.buscarCliente(id);

        clienteService.delete(id);
        flash.addFlashAttribute("success","Cliente eliminado con éxito!");
        uploadFileService.delete(cliente.get().getFoto());
        return "redirect:/listar";
    }




    //@RequestParam sirve para recibir el parametro desde el objeto HTTPServletRequest

    @PostMapping("/form") // el valid sirve para validar si hay algun error, las validaciones las declare en el Model Cliente por ej @NotEmpty
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model, @RequestParam("file")MultipartFile foto, RedirectAttributes flash, SessionStatus status)  {
        String mensaje= (cliente.getId() != null)?"Editar cliente":"Guardar cliente";
        String mensaje2 = (cliente.getId() != null)?"Editar cliente":"Formulario de cliente";
        if(result.hasErrors()) {
            model.addAttribute("mensaje",mensaje);
            model.addAttribute("titulo", mensaje2);
            return "form";

        }
        if(!foto.isEmpty()) {
            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
                uploadFileService.delete(cliente.getFoto());
            }
            try {
                cliente.setFoto(uploadFileService.copy(foto));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String mensajeFlash = (cliente.getId() != null)?"El cliente se ha modificado con éxito!":"Se ha agregado con éxito al cliente!";
        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success",mensajeFlash);
        return "redirect:listar";
    }


    @GetMapping("/ver/{id}")
    public String verCliente(@PathVariable Long id,Model model,RedirectAttributes flash){
        Optional<Cliente> cliente=clienteService.buscarCliente(id);
        if(cliente.isEmpty()){
            flash.addFlashAttribute("danger", "El cliente no existe!");
            return "redirect:/listar";
        }
        model.addAttribute("cliente",cliente);
        model.addAttribute("titulo","Detalle del cliente: " + cliente.get().getNombre() + " " + cliente.get().getApellido());
        return "ver";
    }

    @GetMapping(value ="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename)  {
        Resource resource = null;
        try {
            resource = uploadFileService.verFoto(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename()+"\"")
                .body(resource); //luego se pasa a la respuesta
    }


    @GetMapping("/buscar")
    public String buscarCliente(@RequestParam(value = "DNI") Integer DNI,@RequestParam(name = "page",defaultValue = "0") int page, @ModelAttribute("clienteDNI") Cliente cliente,Model model,RedirectAttributes flash){
        Optional<Cliente> cliente1 = clienteService.buscarClientePorDNI(DNI);
        if(cliente1.isEmpty()){
            flash.addFlashAttribute("danger","No se ha encontrado el cliente especificado");
            return "redirect:/listar";
        }
        Pageable pageRequest = PageRequest.of(page,4);
        PageRender<Cliente> pageRender = new PageRender<>("/listar",clienteService.findAll(pageRequest));
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",cliente1.get());
        model.addAttribute("page",pageRender);
        return "listar";

    }




//ResponseEntity permite agregar contenido a la respuesta, en este caso un objeto que representa el contenido stream
// (flujo de datos) de la imagen,
// en otras palabras se envía la imagen en bytes al cliente, dentro del cuerpo de la respuesta.
}

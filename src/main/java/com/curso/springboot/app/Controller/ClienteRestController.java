package com.curso.springboot.app.Controller;


import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;


    @GetMapping("/listar")
    public List<Cliente> listarRest() {   //API REST, el listado de los clientes se va a almacenar en el cuerpo de la respuesta
        //de forma automatica al guardarse spring va a deducir que es un rest, puede ser json o xml

        return clienteService.findAll2();
    }




}

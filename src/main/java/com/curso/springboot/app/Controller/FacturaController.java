package com.curso.springboot.app.Controller;


import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Model.Factura;
import com.curso.springboot.app.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController{

    @Autowired
    private IClienteService clienteService;


    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable Long clienteId, Model model, RedirectAttributes flash){
        Optional<Cliente> cliente = clienteService.buscarCliente(clienteId);
        if(cliente.isEmpty()) {
                flash.addFlashAttribute("error", "El cliente no existe en la base de datos!");
                return "redirect:/listar";
        }
        Factura factura = new Factura();
        factura.setCliente(cliente.get());
        model.addAttribute("titulo","Crear Factura");
        model.addAttribute("factura", factura);
        return "factura/form";

    }


}

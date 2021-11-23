package com.curso.springboot.app.Controller;


import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Model.Factura;
import com.curso.springboot.app.Model.ItemFactura;
import com.curso.springboot.app.Model.Producto;
import com.curso.springboot.app.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
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

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"}) //responseBody transfroma la salida en JSON y la guarda la respuesta en el body
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        return clienteService.findByNombre(term);
    }

    @PostMapping("/form")
    public String guardar(@Valid Factura factura,
                          BindingResult result,
                          Model model,
                          @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                          @RequestParam(name = "cantidad[]",required = false) Integer[] cantidad,
                          RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()){
            model.addAttribute("titulo","Crear Factura");
            return "factura/form";
        }
        if(itemId==null || itemId.length == 0){
            model.addAttribute("titulo","Crear Factura");
            model.addAttribute("danger","Error: la factura no puede estar vacia!");
            return "factura/form";
        }

        for(int i=0; i < itemId.length; i++){
            Producto p = clienteService.findProductoById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(p);

            factura.addItemFactura(linea);
        }
        clienteService.saveFactura(factura);
        status.setComplete();
        flash.addFlashAttribute("success","Factura creada con éxito!");
        return "redirect:/ver/" + factura.getCliente().getId();

    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model,RedirectAttributes flash){
        Factura factura = clienteService.fetchFacturaByIdWithClienteWithItemFacturaWithProducto(id);//clienteService.findFacturaById(id);
        if(factura==null){
            flash.addFlashAttribute("danger","La factura no existe en la base de datos!");
            return "redirect:/listar";
        }
        model.addAttribute("factura",factura);
        model.addAttribute("titulo", "Factura: "+ factura.getDescripcion());
        return "factura/ver";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,RedirectAttributes flash){

    Factura factura = clienteService.findFacturaById(id);
    if(factura != null){
        clienteService.deleteFactura(id);
        flash.addFlashAttribute("success","Factura eliminada con éxito");
        return "redirect:/ver/" + factura.getCliente().getId();
    }
    flash.addFlashAttribute("danger","La factura no existe en la base de datos, no se pudo eliminar!");
    return "redirect:/listar";
    }

}


package com.curso.springboot.app.view.json;

import com.curso.springboot.app.Model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {


    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("titulo");
        model.remove("page");
        model.remove("clienteDNI");


        return super.filterModel(model);
    }
}

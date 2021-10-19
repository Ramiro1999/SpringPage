package com.curso.springboot.app.Service;

import com.curso.springboot.app.Model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    public Page<Cliente> findAll(Pageable pageable);
    public void save(Cliente cliente);
    public Optional<Cliente> buscarCliente(Long id);
    public void delete(Long id);
    public Optional<Cliente> buscarClientePorDNI(Integer id);

}

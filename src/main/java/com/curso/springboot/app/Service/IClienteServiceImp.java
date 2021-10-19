package com.curso.springboot.app.Service;

import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IClienteServiceImp implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;



    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);

    }

    @Override
    public Optional<Cliente> buscarCliente(Long id) {
       return clienteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Optional<Cliente> buscarClientePorDNI(Integer id) {
        return clienteRepository.findClienteByDNI(id);
    }


}

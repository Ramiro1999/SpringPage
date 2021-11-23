package com.curso.springboot.app.Service;

import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Model.Factura;
import com.curso.springboot.app.Model.Producto;
import com.curso.springboot.app.Repository.ClienteRepository;
import com.curso.springboot.app.Repository.FacturaRepository;
import com.curso.springboot.app.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IClienteServiceImp implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

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

    @Override
    public List<Producto> findByNombre(String term) {
        return productoRepository.findByNombreLikeIgnoreCase("%"+term+"%");
    }

    @Override
    public void saveFactura(Factura f) {
        facturaRepository.save(f);
    }

    @Override
    public Producto findProductoById(Long id) {
       return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Factura findFacturaById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    public void deleteFactura(Long id){
        facturaRepository.deleteById(id);
    }

    @Override
    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaRepository.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }


}

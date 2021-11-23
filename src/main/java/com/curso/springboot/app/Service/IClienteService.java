package com.curso.springboot.app.Service;

import com.curso.springboot.app.Model.Cliente;
import com.curso.springboot.app.Model.Factura;
import com.curso.springboot.app.Model.Producto;
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

    public List<Producto> findByNombre(String term);

    public void saveFactura(Factura f);

    public Producto findProductoById(Long id);

    public Factura findFacturaById(Long id);

    public void deleteFactura(Long id);

    public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long id);



}

package com.curso.springboot.app.Repository;

import com.curso.springboot.app.Model.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FacturaRepository extends CrudRepository<Factura,Long> {

    @Query("SELECT f from Factura f JOIN FETCH f.cliente c JOIN FETCH f.itemFacturas l JOIN FETCH l.producto WHERE f.id=?1")
    public Factura  fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

}

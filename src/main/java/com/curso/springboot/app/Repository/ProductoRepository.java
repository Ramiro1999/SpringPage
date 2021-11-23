package com.curso.springboot.app.Repository;

import com.curso.springboot.app.Model.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProductoRepository extends CrudRepository<Producto, Long> {



    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %?1%")
    public List<Producto> findByNombre(String term);


    public List<Producto> findByNombreLikeIgnoreCase(String term);
}

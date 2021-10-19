package com.curso.springboot.app.Repository;

import com.curso.springboot.app.Model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente,Long> {


     Optional<Cliente> findClienteByDNI(Integer DNI);




}

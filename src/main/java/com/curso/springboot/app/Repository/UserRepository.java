
package com.curso.springboot.app.Repository;


import com.curso.springboot.app.Model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends CrudRepository<Usuario,Long> {




    public Usuario findByUsername(String username);


}


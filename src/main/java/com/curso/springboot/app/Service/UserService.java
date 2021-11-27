

package com.curso.springboot.app.Service;

import com.curso.springboot.app.Model.Role;
import com.curso.springboot.app.Model.Usuario;
import com.curso.springboot.app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByUsername(s);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role : usuario.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getAuthority())); //se registran los roles en el tipo de spring security
        }
        return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnabled(),true,true,true,authorities);
    }
}


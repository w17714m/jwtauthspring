/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.jwt.server.authentication.model.security.Authority;
import com.jwt.server.authentication.model.security.User;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class JwtUserFactory {

    public JwtUserFactory() {
    }
    
     public static JwtUser create(User user) {
//        return new JwtUser(
//                user.getId(),
//                user.getUsername(),
//                user.getFirstname(),
//                user.getLastname(),
//                user.getEmail(),
//                user.getPassword(),
//                mapToGrantedAuthorities(user.getAuthorities()),
//                user.getEnabled(),
//                user.getLastPasswordResetDate()
//        );
        
List<GrantedAuthority> list = new ArrayList<>();
list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                JwtUser usuario = new JwtUser(
                1l,
                "user",
                "primer nombre",
                "segundo nombre",
                "correo",
                "password",
                list,
                true,
                null
        );
                return usuario;
    }
     
     
          public static JwtUser create(String user) {

        
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("USER"));
        JwtUser usuario = new JwtUser(
                1l,
                user,
                "primer nombre",
                "segundo nombre",
                "correo",
                "password",
                list,
                true,
                null
        );
                return usuario;
    }
     
     private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
    
    
}

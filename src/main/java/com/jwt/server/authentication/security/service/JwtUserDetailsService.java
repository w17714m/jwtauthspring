/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.security.service;

import com.jwt.server.authentication.model.security.User;
import com.jwt.server.authentication.security.JwtUserFactory;
import com.jwt.server.authentication.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = userRepository.findByUsername(username);
        return JwtUserFactory.create(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(String.format("No se encuentro el usuario '%s'.", username));
//        } else {
//            return JwtUserFactory.create(user);
//        }
    }
    
}

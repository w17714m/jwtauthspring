/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.security;


import java.util.Collection;
import java.util.Collections;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author USER
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String usuario = a.getName();
        String contrasenia =a.getCredentials().toString();
        return new UsernamePasswordAuthenticationToken(usuario, contrasenia,Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.security;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author USER
 */
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest hsr, HttpServletResponse hsr1, FilterChain fc) throws ServletException, IOException {
        
         final String requestHeader = hsr.getHeader(this.tokenHeader);
        
        String username = null;
        String authToken = null;
        
        /*obtiene la cacera del request*/
         if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = this.jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Se generó un error al intentar obtener el usuario del token.", e);
            } catch (ExpiredJwtException e) {
                logger.warn("el token a expirado ya no es válido.", e);
            }
        } else {
            logger.warn("no se pudo obtenerel  bearer , se ignoró el header");
        }
        
         //el usuario ya se autentico
         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            //obtiene la información de la base de datos.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //se verifica la validez del token y refresca el security context
            if (this.jwtTokenUtil.validateToken(authToken, username)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                //se guarda los detalles de la autenticación en el request.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(hsr));
                //se genera la autorización a la aplicación
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
         
         fc.doFilter(hsr, hsr1);
    }

}

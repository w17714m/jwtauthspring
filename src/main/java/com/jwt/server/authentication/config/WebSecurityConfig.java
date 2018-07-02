/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.config;

import com.jwt.server.authentication.security.CustomAuthenticationProvider;
import com.jwt.server.authentication.security.JwtAuthenticationEntryPoint;
import com.jwt.server.authentication.security.JwtAuthorizationTokenFilter;
import com.jwt.server.authentication.security.JwtTokenUtil;
import com.jwt.server.authentication.security.service.JwtUserDetailsService;
import java.beans.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author USER
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint UnAuthenticacionEntryPoint;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;
    
    @Value("${jwt.header}")
    private String tokenHeader;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .userDetailsService(jwtUserDetailsService)
//            .passwordEncoder(passwordEncoderBean());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.inMemoryAuthentication().withUser("usuario")
                .password(passwordEncoderBean().encode("usuario"))
                .roles("USER");
    }
     
    
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
     
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(UnAuthenticacionEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/h2-console/**/**").permitAll()//se permite el acceso a la consola h2
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
        
        
        JwtAuthorizationTokenFilter  jwtAuthorizationTokenFilter = new JwtAuthorizationTokenFilter(userDetailsService(), this.jwtTokenUtil, this.tokenHeader);
         httpSecurity
            .addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        httpSecurity
                .headers()
                .frameOptions().sameOrigin() //habilita la consola.
                .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        HttpMethod.POST,
                        authenticationPath
                )
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                )
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

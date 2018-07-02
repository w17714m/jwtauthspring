/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.model.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author USER
 */
@Entity
@Table(name="AUTHORITY")
public class Authority {
    
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "authority_seg")
    @SequenceGenerator(name = "authority_seg", sequenceName = "authority_seg",allocationSize = 1)
    private Long id;
    
    @Column(name = "NAME")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;
    
    @ManyToMany(mappedBy = "authorities" , fetch = FetchType.LAZY)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityName getName() {
        return name;
    }

    public void setName(AuthorityName name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
    
}

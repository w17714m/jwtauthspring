/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.server.authentication.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USER
 */
@RestController
public class PersonRestService {
    private static final List<Person> persons;

    static {
        persons = new ArrayList<>();
        persons.add(new Person("w17714m", "w17714m"));
        persons.add(new Person("m41771w", "m41771w"));
    }

    @RequestMapping(path = "/persons", method = RequestMethod.GET)
    public static List<Person> getPersons() {
        return persons;
    }

    @RequestMapping(path = "/persons/{name}", method = RequestMethod.GET)
    public static Person getPerson(@PathVariable("name") String name) {
        return persons.stream()
                .filter(person -> name.equalsIgnoreCase(person.getName()))
                .findAny().orElse(null);
    }
}

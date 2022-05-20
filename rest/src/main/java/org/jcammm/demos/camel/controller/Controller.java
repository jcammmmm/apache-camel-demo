package org.jcammm.demos.camel.controller;

import java.util.List;

import org.jcammm.demos.camel.dto.Contact;
import org.jcammm.demos.camel.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private ContactRepository repository;

    public Contact get(String uuid) {
        return repository.findById(uuid);
    }

    public List<Contact> get(String firstName, String lastName) {
        Contact query = Contact.builder()
            .contactId("*")
            .firstName(firstName == null ? "*" : firstName)
            .lastName(lastName == null ? "*" : lastName)
            .phoneNumber("*")
            .emailAddress("*")
            .streetAddress("*")
            .build();

        return repository.find(query);
    }

    public List<Contact> all() {
        return repository.find(new Contact("*", "*", "*", "*", "*", "*"));
    }
}

package org.jcammm.demos.camel.controller;

import java.util.List;

import org.jcammm.demos.camel.dto.Contact;
import org.jcammm.demos.camel.repository.ContactRepository;
import org.jcammm.demos.camel.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private UUIDGenerator uGenerator;

    public Contact post(Contact c) {
        c.setContactId(uGenerator.get());
        return repository.insert(c);
    }

    public Contact put(String uuid, Contact c) {
        c.setContactId(uuid);
        return repository.insert(c);
    }

    public Contact patch(String uuid, Contact c) {
        c.setContactId(uuid);
        return repository.update(c);
    }

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

    public Contact delete(String uuid) {
        return repository.delete(uuid);
    }
}

package org.jcammm.demos.camel.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    /**
     * @param c the contact info. to store
     * @return the uuid for the new created contact
     */
    public Map<String, String> post(Contact c) {
        c.setContactId(uGenerator.get());
        repository.insert(c);
        return Collections.singletonMap("uuid", c.getContactId());
    }

    /**
     * @param uuid the contact uuid to modify.
     * @param c the new info to replace.
     * @return the previous data for the contact.
     */
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

package org.jcammm.demos.camel.repository;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jcammm.demos.camel.dto.Contact;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * This repository class only interacts with Contact objects that holds the equals method
 * on that is based in the any string '*' contract.
 */
@Component
public class ContactRepository {
    public static Map<String, Contact> contacts = Collections.synchronizedMap(new HashMap<>());

    /**
     * Inserts a contact on the hashmap. We use hashmap in order to have O(1) complexity
     * when user searchs by id.
     * @param c the contact to insert
     * @return the inserted contact
     */
    public Contact insert(Contact c) {
        return contacts.put(c.getContactId(), c);
    }

    /**
     * Replaces the contacts that matches the query with the provided update.
     * @param query
     * @param update
     * @return
     */
    public List<String> replace(Contact query, Contact update) {
        List<String> updates = new LinkedList<>();
        for(Contact c : contacts.values())
            if (c.equals(query)) {
                c = update;
                updates.add(c.getContactId());
            }
        return updates;
    }

    /**
     * First search the ids affected and then removes it to avoid 
     * {@link ConcurrentModificationException}
     * @param query
     * @return
     */
    public List<String> delete(Contact query) {
        List<String> result = new LinkedList<>();
        for (Contact c : contacts.values())
            if (c.equals(query))
                result.add(c.getContactId()); 
        for (String id : result)
            contacts.remove(id);
        return result;
    }

    /**
     * Returns the contact asociated with the provided id
     * @param id
     * @return
     */
    public Contact findById(String id) {
        return contacts.get(id);
    }

    /**
     * Returns the contacts that are equals to the query contact provided.
     * See the {@code equals()} implementation for more details in how this
     * query is performed.
     * @param query
     * @return
     */
    public List<Contact> find(Contact query) {
        List<Contact> result = new LinkedList<>();
        for (Contact c : contacts.values())
            if (c.equals(query))
                result.add(c);
        return result;
    }

    public String toString() {
        return contacts.toString();
    }

    public static void main(String[] args) {
        ContactRepository cr = new ContactRepository();
        cr.insert(new Contact("1", "uno", "eins", "un", "one", "um"));
        System.out.println(cr);
    }
}

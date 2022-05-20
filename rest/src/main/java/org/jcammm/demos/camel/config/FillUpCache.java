package org.jcammm.demos.camel.config;


import org.jcammm.demos.camel.dto.Contact;
import org.jcammm.demos.camel.repository.ContactRepository;
import org.jcammm.demos.camel.service.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FillUpCache implements CommandLineRunner {

    @Autowired
    ContactRepository repository;

    @Autowired
    UUIDGenerator uuidGenerator;

    @Override
    public void run(String... args) throws Exception {

        repository.insert(
            Contact.builder()
                .contactId(uuidGenerator.get())
                .firstName("Juan")
                .lastName("Camilo")
                .phoneNumber("333221111")
                .emailAddress("juan@camilo.xyz")
                .streetAddress("Av 11 12 13")
                .build());

        repository.insert(
            Contact.builder()
                .contactId(uuidGenerator.get())
                .firstName("Frank")
                .lastName("Chevysheb")
                .phoneNumber("333221111")
                .emailAddress("juan@camilo.xyz")
                .streetAddress("Av 11 12 13")
                .build());

        repository.insert(
            Contact.builder()
                .contactId(uuidGenerator.get())
                .firstName("Heinrich")
                .lastName("Bocadro")
                .phoneNumber("333221111")
                .emailAddress("juan@camilo.xyz")
                .streetAddress("Av 11 12 13")
                .build());
                
        repository.insert(
            Contact.builder()
                .contactId(uuidGenerator.get())
                .firstName("Mildrek")
                .lastName("Haslnok")
                .phoneNumber("333221111")
                .emailAddress("juan@camilo.xyz")
                .streetAddress("Av 11 12 13")
                .build());
    }
    
}

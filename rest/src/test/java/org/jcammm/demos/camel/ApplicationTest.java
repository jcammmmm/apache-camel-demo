package org.jcammm.demos.camel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.jcammm.demos.camel.dto.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTest {


    @LocalServerPort
	private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void post() {
        String url = "http://localhost:" + port + "/contacts";
        Contact c = Contact.builder()
            .contactId("99663413-c8b4-4cb0-901b-4c3cc06eda31")
            .firstName("Juan")
            .lastName("Camilo")
            .phoneNumber("333221111")
            .emailAddress("juan@camilo.xyz")
            .streetAddress("Av 11 12 13")
            .build();
        ResponseEntity<Contact> response = restTemplate.postForEntity(url, c, Contact.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void get() {
        String url = "http://localhost:" + port + "/contacts";
        Contact c1 = Contact.builder()
            .contactId("99663413-c8b4-4cb0-901b-4c3cc06eda31")
            .firstName("Juan")
            .lastName("Camilo")
            .phoneNumber("+8394894949")
            .emailAddress("juan@camilo.xyz")
            .streetAddress("Av 11 12 13")
            .build();

        Contact c2 = Contact.builder()
            .contactId("99663413-c8b4-4cb0-901b-4c3cc06eda32")
            .firstName("Mildrek")
            .lastName("Haslnok")
            .phoneNumber("+13333221111")
            .emailAddress("mild@hasl.net")
            .streetAddress("Av 66 77 99")
            .build();
        
        restTemplate.postForEntity(url, c1, Contact.class);
        restTemplate.postForEntity(url, c2, Contact.class);

        ResponseEntity<List> response = restTemplate.getForEntity(url + "/all", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size() == 2);
    }


    @Test
    void find() {
        String url = "http://localhost:" + port + "/contacts";
        Contact c1 = Contact.builder()
            .contactId("99663413-c8b4-4cb0-901b-4c3cc06eda31")
            .firstName("Juan")
            .lastName("Camilo")
            .phoneNumber("+8394894949")
            .emailAddress("juan@camilo.xyz")
            .streetAddress("Av 11 12 13")
            .build();

        Contact c2 = Contact.builder()
            .contactId("99663413-c8b4-4cb0-901b-4c3cc06eda32")
            .firstName("Mildrek")
            .lastName("Haslnok")
            .phoneNumber("+13333221111")
            .emailAddress("mild@hasl.net")
            .streetAddress("Av 66 77 99")
            .build();
        
        restTemplate.postForEntity(url, c1, Contact.class);
        restTemplate.postForEntity(url, c2, Contact.class);

        ResponseEntity<List> response = restTemplate.getForEntity(url + "/find?lastName=Camilo", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size() == 1);
    }

}

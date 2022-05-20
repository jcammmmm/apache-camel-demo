package org.jcammm.demos.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.jcammm.demos.camel.dto.Contact;
import org.jcammm.demos.camel.exception.DataOutOfFieldException;
import org.jcammm.demos.camel.utils.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication // (exclude = { WebSocketServletAutoConfiguration.class, AopAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class, EmbeddedWebServerFactoryCustomizerAutoConfiguration.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean<CamelHttpTransportServlet> servletRegistrationBean() {
        ServletRegistrationBean<CamelHttpTransportServlet> servlet = 
            new ServletRegistrationBean<CamelHttpTransportServlet>(new CamelHttpTransportServlet(), "/*");
        servlet.setName("CamelServlet");
        return servlet;
    }


    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
        restConfiguration()
                .component("servlet")
                .apiContextPath("/api-doc")
                .bindingMode(RestBindingMode.json);

        rest("/contacts")
                .consumes("application/json")
                .produces("application/json")
                // HTTP: POST /contacts
                .post()
                    .description("Store a contact.")
                    // .route()
                    //     .doTry()
                    //         .process(new Validator())
                    //     .doCatch(DataOutOfFieldException.class)
                    //         .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                    //         .to("bean:controllerAdvisor?method=advice(${exception.message})")
                    //     .endDoTry()
                    // .endRest()
                    .type(Contact.class)
                    .outType(Contact.class)
                    .to("bean:controller?method=post")
                // HTTP: PUT
                .put()
                    .description("Replace a contact given a contact with its id.")
                    .type(Contact.class)
                    .outType(Contact.class)
                    .to("bean:controller?method=put")
                    // HTTP PATCH
                    .patch()
                    .description("Updates a contact given a contact with its id.")
                    .type(Contact.class)
                    .outType(Contact.class)
                    .to("bean:controller?method=patch")
                // HTTP: GET /contacts/all
                .get("/all")
                    .description("Retrieve all contacts (without pagination).")
                    .outType(List.class)
                    .to("bean:controller?method=all")
                // HTTP: GET /contacts/745bfd98-4192-4f04-acae-c0dfed9d12fc
                .get("/{id}")
                    .description("Obtain one contact with its id.")
                    .outType(List.class)
                    .to("bean:controller?method=get(${header.id})")
                // HTTP: GET /contacts/find?firstName=Juan&lastName=Camilo
                .get("/find")
                    .description("Find a contacts by its first or last name")
                    .outType(Contact.class)
                    .to("bean:controller?method=get(${header.firstName}, ${header.lastName})");
        }
    }
}

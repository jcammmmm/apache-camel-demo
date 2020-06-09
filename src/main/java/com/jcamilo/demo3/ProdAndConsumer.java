package com.jcamilo.demo3;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;




public class ProdAndConsumer {
  public static void main(String... args) throws Exception {

    // Create the context
    /**
     * 
        Create a CamelContext object.

        Add endpoints – and possibly components, which are discussed in Section 4.5 ("Components") – to the CamelContext object.

        Add routes to the CamelContext object to connect the endpoints.

        Invoke the start() operation on the CamelContext object. This starts Camel-internal threads that are used to process the sending, receiving and processing of messages in the endpoints.

        Eventually invoke the stop() operation on the CamelContext object. Doing this gracefully stops all the endpoints and Camel-internal threads.
     */
    CamelContext context = new DefaultCamelContext();

    // Add a router
    context.addRoutes(new RouteBuilder(){
    
      @Override
      public void configure() throws Exception {
        from("direct:start")
        .to("seda:end");
      }
    });

    // start the camel context
    context.start();

    ProducerTemplate producerTemplate = context.createProducerTemplate();
    producerTemplate.sendBody("direct:start", "example example!");

    ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
    String mssg = consumerTemplate.receiveBody("seda:end", String.class);

    System.out.println(mssg);
  }
}
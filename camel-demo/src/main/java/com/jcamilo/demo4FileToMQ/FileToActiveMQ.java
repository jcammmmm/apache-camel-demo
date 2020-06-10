package com.jcamilo.demo4FileToMQ;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FileToActiveMQ {
  public static void main(String[] args) throws Exception {

    // The cammel context
    CamelContext context = new DefaultCamelContext();

    // Connection to ActiveMQ
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));


    context.addRoutes(new RouteBuilder(){
    
      @Override
      public void configure() throws Exception {
        from("file:input_box?noop=true")
        .to("activemq:queue:jcamiloqueue");
      }
    });
    
    while(true)
      context.start();
  }
}
package com.jcamilo.demo2;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Automatically creates in the root folder of this project a folder named 'input_box'.
 * Whenever file you drop in 'input_box' automatically a folder called 'output_box' is 
 * created and then the file is copied.
 * We write an infinite loop to do that with any files you drop in.
 */
public class FileCopy {
  public static void main(String... args) {
    CamelContext context = new DefaultCamelContext();
    try {
      context.addRoutes(new RouteBuilder() {

        @Override
        public void configure() throws Exception {
          from("file:input_box?noop=true").to("file:output_box");
        }
      });
      
      while(true)
        context.start();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
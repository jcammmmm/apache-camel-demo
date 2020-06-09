package com.jcamilo;

import org.apache.camel.builder.RouteBuilder;

public class Route extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    System.out.println("example example!");
  }
  
}
package com.jcamilo;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public final class App {
  private App() {

  }

  public static void main(String[] args) {
    CamelContext context = new DefaultCamelContext();
    try {
      context.addRoutes(new Route());
      context.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

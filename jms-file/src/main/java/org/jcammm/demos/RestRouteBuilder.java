package org.jcammm.demos;

import org.apache.camel.builder.RouteBuilder;

public class RestRouteBuilder extends RouteBuilder {
    
    @Override
    public void configure() {
        // your messages will be written to a file located in './target/messages'
        from("test-jms:queue:test.queue").to("file:target/messages");
    }
}

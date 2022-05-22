package org.jcammm.demos;

import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 * https://camel.apache.org/manual/walk-through-an-example.html
 * https://github.com/apache/camel-examples/blob/main/examples/jms-file/pom.xml
 */
public class App {
    public static void main( String[] args ) throws Exception {

        try (CamelContext ctx = new DefaultCamelContext()) {
            ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
            connFactory.setTrustAllPackages(true);
            connFactory.setTrustedPackages(Arrays.asList("org.jcammm.demos"));

            ctx.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connFactory));
            ctx.addRoutes(new RestRouteBuilder());

            ctx.start();

            try (ProducerTemplate template = ctx.createProducerTemplate()) {
                for (int i = 0; i < 10; i++) {
                    template.sendBody("test-jms:queue:test.queue", "Mensaje de prueba: " + i);
                }
            }

            Thread.sleep(1000L);
        }

    }
}

package com.radcortez.tomee.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Named;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Named
public class EchoHttpRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_QUERY, simple("echo=${body}"))
                .to("http://localhost:4204/openejb/example/")
                .convertBodyTo(String.class)
                .to("stream:out");
    }
}

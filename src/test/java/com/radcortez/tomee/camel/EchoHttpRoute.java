package com.radcortez.tomee.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Named;
import javax.ws.rs.core.MediaType;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Named
public class EchoHttpRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:get")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_QUERY, simple("echo=${body}"))
                .to("http://localhost:4204/openejb/echo/")
                .convertBodyTo(String.class)
                .to("stream:out");

        from("direct:post")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_XML))
                .setBody(simple("<echo><value>${body}</value></echo>"))
                .to("http://localhost:4204/openejb/echo/")
                .convertBodyTo(String.class)
                .to("stream:out");
    }
}

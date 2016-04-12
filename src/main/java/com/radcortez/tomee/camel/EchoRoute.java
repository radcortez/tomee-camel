package com.radcortez.tomee.camel;

import org.apache.camel.builder.RouteBuilder;

import javax.inject.Named;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Named
public class EchoRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("cxfrs://echo")
                .convertBodyTo(String.class)
                .to("stream:out");
    }
}

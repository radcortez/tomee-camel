package com.radcortez.tomee.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;

import javax.inject.Named;
import javax.ws.rs.core.MediaType;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Named
public class EchoRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(HttpOperationFailedException.class).continued(true);

        from("direct:get")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_QUERY, simple("echo=${body}"))
                .to("http4://localhost:4204/openejb/echo/");

        from("direct:post")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_XML))
                .convertBodyTo(String.class)
                .log("Payload:\n ${body}")
                .to("http4://localhost:4204/openejb/echo/?throwExceptionOnFailure=false")
                .log("Response Status Code: ${header." + Exchange.HTTP_RESPONSE_CODE + "}")
                .convertBodyTo(String.class);
    }
}

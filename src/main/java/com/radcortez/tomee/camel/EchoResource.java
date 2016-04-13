package com.radcortez.tomee.camel;

import org.apache.camel.CamelContext;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Path("/echo")
@Produces({MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_XML})
public class EchoResource {
    @Inject
    private CamelContext camelContext;

    @GET
    public String echoGet(@QueryParam(value = "echo") final String echo) {
        return echo;
    }

    @POST
    public String echoPost(final EchoDto echo) {
        return camelContext.createProducerTemplate().requestBody("direct:rest-post", echo, String.class);
    }
}

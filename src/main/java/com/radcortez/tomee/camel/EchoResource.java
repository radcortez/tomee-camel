package com.radcortez.tomee.camel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
    @GET
    public String echo(@QueryParam(value = "echo") final String echo) {
        return echo;
    }
}

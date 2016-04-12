package com.radcortez.tomee.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.Uri;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Jars;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@RunWith(ApplicationComposer.class)
@EnableServices(value = "jaxrs")
public class EchoResourceTest {
    @Inject
    private CamelContext camelContext;

    @Inject
    @Uri("direct:start")
    private ProducerTemplate producerTemplate;

    @Module
    @Jars("camel-cdi")
    @Classes(cdi = true, value = {EchoResource.class, EchoHttpRoute.class})
    public WebApp webApp() {
        return new WebApp();
    }

    @Test
    public void testEcho() throws Exception {
        final String response = WebClient.create("http://localhost:4204")
                                         .path("/openejb/example/")
                                         .query("echo", "Hi!")
                                         .get(String.class);
        System.out.println("response = " + response);
        assertEquals("Hi!", response);
    }

    @Test
    public void testEchoCamel() throws Exception {
        camelContext.start();
        producerTemplate.sendBody("Hi!");
    }
}

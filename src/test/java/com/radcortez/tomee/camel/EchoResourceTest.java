package com.radcortez.tomee.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.cdi.Uri;
import org.apache.cxf.jaxrs.client.ResponseReader;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Jars;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static java.util.Collections.singletonList;
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
    @Uri("direct:get")
    private ProducerTemplate producerTemplate;

    @Module
    public PersistenceUnit partnerPu() {
        final PersistenceUnit camelPu = new PersistenceUnit("camelPu");
        camelPu.addClass(Echo.class);
        camelPu.setExcludeUnlistedClasses(true);
        camelPu.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
        camelPu.setProperty("openjpa.Log", "SQL=Trace");
        camelPu.setProperty("openjpa.ConnectionFactoryProperties", "PrintParameters=true");
        return camelPu;
    }

    @Module
    @Jars("camel-cdi")
    @Classes(cdi = true, value = {TypeConverters.class, EchoResource.class, EchoRoute.class, EchoHttpRoute.class})
    public WebApp webApp() {
        return new WebApp();
    }

    @Test
    public void testEcho() throws Exception {
        final String responseGet = WebClient.create("http://localhost:4204")
                                            .path("/openejb/echo/")
                                            .query("echo", "Hi!")
                                            .get(String.class);
        System.out.println("responseGet = " + responseGet);
        assertEquals("Hi!", responseGet);

        final String responsePost = ((String) WebClient.create("http://localhost:4204",
                                                               singletonList(new ResponseReader(String.class)))
                                                       .path("/openejb/echo/")
                                                       .post(EchoDto.of("Hi!"))
                                                       .getEntity());
        System.out.println("responsePost = " + responsePost);
        assertEquals("Hi!", responsePost);
    }

    @Test
    public void testEchoCamel() throws Exception {
        camelContext.start();

        camelContext.createProducerTemplate().sendBody("direct:post", EchoDto.of("Hi!"));
        camelContext.createProducerTemplate().sendBody("direct:post", EchoDto.of(null));
    }
}

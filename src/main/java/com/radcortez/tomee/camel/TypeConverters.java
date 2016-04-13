package com.radcortez.tomee.camel;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.bean.validator.BeanValidationException;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.status;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Converter
public class TypeConverters {
    @Converter
    public Echo convert(final EchoDto echoDto) {
        return Echo.builder().id(0L).value(echoDto.getValue()).build();
    }

    @Converter
    public EchoDto convert(final Echo echo) {
        return EchoDto.of(echo.getValue());
    }

    @Converter
    public Response convert(final EchoDto echoDto, final Exchange exchange) {
        final Optional<BeanValidationException> validationException =
                Optional.ofNullable(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, BeanValidationException.class));

        return validationException.map(e -> status(BAD_REQUEST).build())
                                  .orElse(status(CREATED).entity(echoDto.getValue()).build());
    }

    @Converter
    public String marshal(final EchoDto echoDto) throws Exception {
        final StringWriter stringWriter = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(EchoDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(echoDto, stringWriter);
        return stringWriter.toString();
    }
}

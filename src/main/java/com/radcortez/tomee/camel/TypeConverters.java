package com.radcortez.tomee.camel;

import org.apache.camel.Converter;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Converter
public class TypeConverters {
    @Converter
    public Echo convert(EchoDto echoDto) {
        return Echo.builder().id(0L).value(echoDto.getValue()).build();
    }
}

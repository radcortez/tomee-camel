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
    public Echo convert(final EchoDto echoDto) {
        return Echo.builder().id(0L).value(echoDto.getValue()).build();
    }

    @Converter
    public String convert(final Echo echo) {
        return echo.getValue();
    }
}

package com.radcortez.tomee.camel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@XmlRootElement(name = "echo")
@XmlType(name = "echo")
@XmlAccessorType(FIELD)
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
public class EchoDto {
    @NotNull
    private String value;
}

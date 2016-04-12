package com.radcortez.tomee.camel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import static lombok.AccessLevel.PRIVATE;

/**
 * Description.
 *
 * @author Roberto Cortez
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Getter
@Builder
public class Echo {
    @Id
    private Long id;
    private String value;
}

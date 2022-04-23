package com.veglad.servicesmarthome.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {

    private Integer id;

    @JsonIgnore
    private String auth0Id;

    private String firstName;

    private String lastName;

    private String email;
}

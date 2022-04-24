package com.veglad.servicesmarthome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Auth0PersonRegisterRequestDto {

    @JsonProperty("user_id")
    private String auth0Id;

    @JsonProperty("given_name")
    private String firstName;

    @JsonProperty("family_name")
    private String lastName;

    private String name;

    private String nickname;

    private String email;
}

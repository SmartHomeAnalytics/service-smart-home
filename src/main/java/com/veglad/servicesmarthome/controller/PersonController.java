package com.veglad.servicesmarthome.controller;

import com.veglad.servicesmarthome.dto.Auth0PersonRegisterRequestDto;
import com.veglad.servicesmarthome.dto.PersonDto;
import com.veglad.servicesmarthome.dto.api.ApiResponse;
import com.veglad.servicesmarthome.service.PersonService;
import com.veglad.servicesmarthome.service.auth.RequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/person")
public class PersonController {

    private final PersonService personService;

    private final RequestContextHolder requestContextHolder;

    public PersonController(PersonService personService,
                            RequestContextHolder requestContextHolder) {
        this.personService = personService;
        this.requestContextHolder = requestContextHolder;
    }

    @GetMapping("/me")
    public ApiResponse<PersonDto> getMe() {
        return ApiResponse.of(this.personService.getById(this.requestContextHolder.getContext().getPersonId()));
    }

    @PostMapping("/register")
    public ApiResponse<PersonDto> registerUser(@RequestBody Auth0PersonRegisterRequestDto auth0Person) {
        PersonDto personDto = new PersonDto();

        personDto.setAuth0Id(auth0Person.getAuth0Id());
        personDto.setEmail(auth0Person.getEmail());
        personDto.setFirstName(Optional.ofNullable(auth0Person.getFirstName())
                .orElse(Optional.ofNullable(auth0Person.getName()).orElse(auth0Person.getNickname())));
        personDto.setLastName(auth0Person.getLastName());

        return ApiResponse.of(this.personService.registerNewAuth0User(personDto));
    }
}

package com.veglad.servicesmarthome.service.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.veglad.servicesmarthome.error.EntityNotFound;
import com.veglad.servicesmarthome.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestContextProvider {

    private final PersonService personService;

    @Autowired
    public RequestContextProvider(PersonService personService) {
        this.personService = personService;
    }

    public Integer getId() {
        String auth0ExtUserId = getAuth0ExtUserId();

        if (auth0ExtUserId == null) {
            return null;
        }

        return Optional.of(auth0ExtUserId)
                .map(this.personService::getPersonIdByAuth0ExtUserId)
                .orElseThrow(() -> new EntityNotFound(String.format("User with given ext id (%s) not found", auth0ExtUserId)));
    }

    public String getAuth0ExtUserId() {
        return Optional.ofNullable(getDecodedJWT())
                .map(Payload::getSubject)
                .orElse(null);
    }

    private DecodedJWT getDecodedJWT() {
        return (DecodedJWT) Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getDetails)
                .orElse(null);
    }
}

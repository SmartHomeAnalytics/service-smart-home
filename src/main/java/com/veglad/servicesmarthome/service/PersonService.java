package com.veglad.servicesmarthome.service;

import com.veglad.servicesmarthome.converter.PersonConverter;
import com.veglad.servicesmarthome.dao.PersonDao;
import com.veglad.servicesmarthome.dto.PersonDto;
import com.veglad.servicesmarthome.dto.db.DbPerson;
import com.veglad.servicesmarthome.error.EntityNotFound;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonDao dao;

    private final PersonConverter converter;

    public PersonService(PersonDao dao,
                         PersonConverter converter) {
        this.dao = dao;
        this.converter = converter;
    }

    public PersonDto getById(Integer id) {
        return Optional.ofNullable(id)
                .map(this::getDb)
                .map(this.converter::fromDbToDto)
                .orElseThrow(() -> new EntityNotFound("Person is not found"));
    }

    private DbPerson getDb(Integer id) {
        return dao.findById(id).orElse(null);
    }

    public Integer getPersonIdByAuth0ExtUserId(String auth0ExtId) {
        return dao.getPersonIdByAuth0Id(auth0ExtId).orElse(null);
    }

    public PersonDto registerNewAuth0User(PersonDto personDto) {
        DbPerson dbPerson = dao.getPersonByAuth0Id(personDto.getAuth0Id()).orElse(null);

        if (dbPerson == null) {
            personDto.setId(null);
            dbPerson = Optional.of(personDto)
                    .map(it -> dao.save(converter.fromDtoToDb(personDto)))
                    .get();
        }

        return Optional.of(dbPerson)
                .map(this.converter::fromDbToDto)
                .get();
    }
}

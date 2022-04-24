package com.veglad.servicesmarthome.converter;

import com.veglad.servicesmarthome.dto.PersonDto;
import com.veglad.servicesmarthome.dto.db.DbPerson;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {

    public PersonDto fromDbToDto(DbPerson db) {
        PersonDto dto = new PersonDto();

        dto.setId(db.getId());
        dto.setFirstName(db.getFirstName());
        dto.setLastName(db.getLastName());
        dto.setEmail(db.getEmail());
        dto.setAuth0Id(db.getAuth0Id());

        return dto;
    }

    public DbPerson fromDtoToDb(PersonDto dto) {
        DbPerson db = new DbPerson();

        db.setId(dto.getId());
        db.setFirstName(dto.getFirstName());
        db.setLastName(dto.getLastName());
        db.setEmail(dto.getEmail());
        db.setAuth0Id(dto.getAuth0Id());

        return db;
    }
}

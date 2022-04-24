package com.veglad.servicesmarthome.dao;

import com.veglad.servicesmarthome.dto.db.DbPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonDao extends CrudRepository<DbPerson, Integer> {

    @Query(value = "SELECT p.id FROM person p WHERE p.auth0_id = :auth0Id", nativeQuery = true)
    Optional<Integer> getPersonIdByAuth0Id(String auth0Id);

    @Query(value = "SELECT * FROM person p WHERE p.auth0_id = :auth0Id", nativeQuery = true)
    Optional<DbPerson> getPersonByAuth0Id(String auth0Id);
}

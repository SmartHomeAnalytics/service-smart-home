package com.veglad.servicesmarthome.dto.db;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "person")
@EntityListeners(AuditingEntityListener.class)
public class DbPerson {

    @Id
    @SequenceGenerator(name = "person_id_seq",
            sequenceName = "person_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "person_id_seq")
    private Integer id;

    @Column(name = "auth0_id", updatable = false)
    private String auth0Id;

    private String firstName;

    private String lastName;

    @Column(updatable = false)
    private String email;

    @LastModifiedDate
    private Instant modificationDate;

    @Column(updatable = false)
    @CreatedDate
    private Instant creationDate;
}

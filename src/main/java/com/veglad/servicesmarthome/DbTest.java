package com.veglad.servicesmarthome;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_test")
@Data
public class DbTest {

    @Id
    private Integer id;

    private String name;
}

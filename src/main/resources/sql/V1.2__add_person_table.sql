CREATE SEQUENCE IF NOT EXISTS person_id_seq;

CREATE TABLE IF NOT EXISTS person
(
    "id"                INTEGER   NOT NULL PRIMARY KEY DEFAULT nextval('person_id_seq'),
    "first_name"        varchar(255),
    "last_name"         varchar(255),
    "email"             varchar(255) UNIQUE,
    "auth0_id"          varchar(255) UNIQUE,
    "creation_date"     timestamp NOT NULL             DEFAULT now(),
    "modification_date" timestamp NOT NULL             DEFAULT now()
);

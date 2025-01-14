--liquibase formatted sql

--changeset admin:1
SET search_path TO jpa_core;

create table xxx
(
    id          bigint       not null primary key,
    name        varchar(255) not null,
    description text
);


--liquibase formatted sql

SET search_path TO jpa_core;

--changeset admin:1
create table bar
(
    id          bigint       not null primary key,
    name        varchar(255) not null,
    description text
);


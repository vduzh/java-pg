--liquibase formatted sql

-- SET search_path TO jpa_core;

--changeset admin:1
create table jpa_core.foo
(
    id          bigint       not null primary key,
    name        varchar(255) not null,
    description text
);

-- --changeset admin:2
-- -- Добавление столбца для статуса
-- alter table foo
--     add column status varchar(20);

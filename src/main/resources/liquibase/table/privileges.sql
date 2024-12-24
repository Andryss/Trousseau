--liquibase formatted sql

--changeset andryss:create-privileges-table
create table privileges (
    id text primary key,
    privilege text
);

--changeset andryss:add-admin-privileges
insert into privileges (id, privilege) values ('sup', 'SUPERUSER');

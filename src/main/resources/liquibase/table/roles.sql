--liquibase formatted sql

--changeset andryss:create-roles-table
create table roles (
    id text primary key,
    role text
);

--changeset andryss:add-admin-user-roles
insert into roles (id, role) values ('adm', 'ADMIN'), ('usr', 'USER');

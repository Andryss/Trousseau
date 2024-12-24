--liquibase formatted sql

--changeset andryss:create-role_privileges-table
create table role_privileges (
    role_id text references roles(id),
    privilege_id text references privileges(id),
    primary key (role_id, privilege_id)
);

--changeset andryss:add-admin-superuser-relation
insert into role_privileges (role_id, privilege_id) values ('adm', 'sup');

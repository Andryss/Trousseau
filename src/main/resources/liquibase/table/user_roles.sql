--liquibase formatted sql

--changeset andryss:create-user_roles-table
create table user_roles (
    user_id bigint references users(id),
    role_id text references roles(id),
    primary key (user_id, role_id)
);

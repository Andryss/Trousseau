--liquibase formatted sql

--changeset andryss:create-role_privileges-table
create table role_privileges (
    role_id text references roles(id),
    privilege_id text references privileges(id),
    primary key (role_id, privilege_id)
);

--changeset andryss:add-admin-role-privileges
insert into role_privileges (role_id, privilege_id) select 'adm', id from privileges;

--changeset andryss:add-user-role-privileges
insert into role_privileges (role_id, privilege_id) values ('usr', 'pages.home.view'),
                                                           ('usr', 'pages.profile.view'),
                                                           ('usr', 'pages.search.view'),
                                                           ('usr', 'public'),
                                                           ('usr', 'pages.item.new.view'),
                                                           ('usr', 'actions.item.create'),
                                                           ('usr', 'actions.item.book'),
                                                           ('usr', 'actions.item.save'),
                                                           ('usr', 'actions.subscription.create');

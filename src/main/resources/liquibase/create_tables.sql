create table dormitories (
    id text primary key,
    name text not null,
    address text not null
);

create table users (
    id text primary key,
    login text unique not null,
    password text not null,
    dormitory_id text not null,
    room text
);

create table roles (
    id text primary key,
    role text
);

create table user_roles (
    user_id text,
    role_id text
);

create table privileges (
    id text primary key,
    privilege text
);

create table role_privileges (
    role_id text,
    privilege_id text
);

create table categories (
    id text primary key,
    name text unique not null,
    description text,
    parent text
);

create table items (
    id text primary key,
    title text not null,
    description text not null,
    status text not null,
    user_id text not null,
    created_at timestamp not null
);

create table item_categories (
    item_id text,
    category_id text,
    primary key (item_id, category_id)
);

create table bookings (
    id text primary key,
    user_id text not null,
    item_id text not null,
    booked_at timestamp not null
);

create table saved_items (
    user_id text,
    item_id text,
    saved_at timestamp not null,
    primary key (user_id, item_id)
);

create table subscriptions (
    id text primary key,
    user_id text not null,
    name text not null,
    created_at timestamp not null
);

create table subscription_categories (
    subscription_id text,
    category_id text,
    primary key (subscription_id, category_id)
);

-- основные сущности

create table dormitories (
    id bigserial primary key,
    name varchar(32),
    address varchar(128)
);

create table users (
    id bigserial primary key,
    login varchar(128),
    password varchar,
    role varchar(16),
    dormitory_id bigserial references dormitories(id),
    room varchar(32)
);

create table photos (
    id bigserial primary key,
    data bytea,
    upload_datetime timestamp,
    upload_user_id bigserial references users(id)
);

create table category_groups (
    id bigserial primary key,
    name varchar(32),
    description varchar(256)
);

create table categories (
    id bigserial primary key,
    name varchar(32),
    description varchar(256),
    group_id bigserial references category_groups(id)
);

create table items (
    id bigserial primary key,
    title varchar(64),
    photo_id bigserial references photos(id),
    description varchar(8192),
    status varchar(16),
    user_id bigserial references users(id),
    creation_datetime timestamp
);

create table item_status_history (
    id bigserial primary key,
    item_id bigserial references items(id),
    old_status varchar(16),
    new_status varchar(16),
    change_datetime timestamp,
    change_user_id bigserial references users(id)
);

create table bookings (
    id bigserial primary key,
    user_id bigserial references users(id),
    item_id bigserial references items(id),
    booking_datetime timestamp
);

create table saved_items (
    user_id bigserial references users(id),
    item_id bigserial references items(id),
    added_datetime timestamp,
    primary key (user_id, item_id)
);

create table subscriptions (
    id bigserial primary key,
    user_id bigserial references users(id),
    name varchar(64),
    created_datetime timestamp
);

-- вспомогательные таблицы

create table item_categories (
    item_id bigserial references items(id),
    category_id bigserial references categories(id),
    primary key (item_id, category_id)
);

create table subscription_categories (
    subscription_id bigserial references subscriptions(id),
    category_id bigserial references categories(id),
    primary key (subscription_id, category_id)
);

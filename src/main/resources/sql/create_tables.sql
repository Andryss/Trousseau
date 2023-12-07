-- основные сущности

create table dormitories (
    id bigserial primary key,
    name varchar(32) not null,
    address varchar(128) unique not null
);

create table users (
    id bigserial primary key,
    login varchar(128) unique not null,
    password varchar not null,
    role varchar(16) not null,
    dormitory_id bigserial references dormitories(id) not null,
    room varchar(32) not null
);

create table photos (
    id bigserial primary key,
    data bytea not null,
    upload_datetime timestamp not null,
    upload_user_id bigserial references users(id) not null
);

create table category_groups (
    id bigserial primary key,
    name varchar(32) unique not null,
    description varchar(256) not null
);

create table categories (
    id bigserial primary key,
    name varchar(32) unique not null,
    description varchar(256) not null,
    group_id bigserial references category_groups(id) not null
);

create table items (
    id bigserial primary key,
    title varchar(64) not null,
    photo_id bigserial references photos(id) not null,
    description varchar(1024) not null,
    status varchar(16) not null,
    user_id bigserial references users(id) not null,
    creation_datetime timestamp not null,
    constraint item_min_info check ( length(title) >= 10 and length(description) >= 20 )
);

create table item_status_history (
    id bigserial primary key,
    item_id bigserial references items(id) not null,
    old_status varchar(16) not null,
    new_status varchar(16) not null,
    change_datetime timestamp not null,
    change_user_id bigserial references users(id) not null
);

create table bookings (
    id bigserial primary key,
    user_id bigserial references users(id) not null,
    item_id bigserial references items(id) not null,
    booking_datetime timestamp not null
);

create table saved_items (
    user_id bigserial references users(id),
    item_id bigserial references items(id),
    added_datetime timestamp not null,
    primary key (user_id, item_id)
);

create table subscriptions (
    id bigserial primary key,
    user_id bigserial references users(id) not null,
    name varchar(64) not null,
    created_datetime timestamp not null
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

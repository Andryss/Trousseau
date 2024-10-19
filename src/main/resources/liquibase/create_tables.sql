create table dormitories (
    id text primary key,
    name text not null,
    address text not null
);

create table users (
    id text primary key,
    login text unique not null,
    password text not null,
    dormitory_id text not null references dormitories(id),
    room text
);

create table roles (
    id text primary key,
    role text
);

create table user_roles (
    user_id text references users(id),
    role_id text references roles(id),
    primary key (user_id, role_id)
);

create table privileges (
    id text primary key,
    privilege text
);

create table role_privileges (
    role_id text references roles(id),
    privilege_id text references privileges(id),
    primary key (role_id, privilege_id)
);

create table categories (
    id text primary key,
    name text unique not null,
    description text,
    parent text references categories(id)
);

create table items (
    id text primary key,
    title text not null,
    description text not null,
    status text not null,
    user_id text not null references users(id),
    created_at timestamp not null
);

create table item_categories (
    item_id text references items(id),
    category_id text references categories(id),
    primary key (item_id, category_id)
);

create table bookings (
    id text primary key,
    user_id text not null references bookings(id),
    item_id text not null references items(id),
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
    user_id text not null references users(id),
    name text not null,
    created_at timestamp not null
);

create table subscription_categories (
    subscription_id text references subscriptions(id),
    category_id text references categories(id),
    primary key (subscription_id, category_id)
);

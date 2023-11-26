create extension pg_trgm;

create index if not exists items_title_trgm on items using gin(title gin_trgm_ops);

create index if not exists items_description_trgm on items using gin(description gin_trgm_ops);

create index if not exists categories_name_trgm on categories using gin(name gin_trgm_ops);

create index if not exists bookings_user_id_hash on bookings using hash(user_id);

create index if not exists saved_items_user_id_hash on saved_items using hash(user_id);

create index if not exists subscriptions_user_id_hash on subscriptions using hash(user_id);
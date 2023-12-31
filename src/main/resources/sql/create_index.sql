create index if not exists items_title_trgm on items using gin(to_tsvector('russian', title));

create index if not exists items_description_trgm on items using gin(to_tsvector('russian', description));

create index if not exists categories_name_trgm on categories using gin(to_tsvector('russian', name));

create index if not exists bookings_user_id_hash on bookings using hash(user_id);

create index if not exists saved_items_user_id_hash on saved_items using hash(user_id);

create index if not exists subscriptions_user_id_hash on subscriptions using hash(user_id);
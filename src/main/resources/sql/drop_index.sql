drop index if exists items_title_trgm, items_description_trgm, categories_name_trgm,
    bookings_user_id_hash, saved_items_user_id_hash, subscriptions_user_id_hash;

drop extension if exists pg_trgm;
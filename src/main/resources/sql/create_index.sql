create extension pg_trgm;

create index if not exists items_title_trgm on items using gin(title gin_trgm_ops);

create index if not exists items_description_trgm on items using gin(description gin_trgm_ops);

create index if not exists categories_name_trgm on categories using gin(name gin_trgm_ops);

-- TODO: добавить hash индексы на user_id
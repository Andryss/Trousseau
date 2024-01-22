-- 1. поиск по объявлениям

create or replace function find_items(q_query text default '', q_categories text[] default '{}')
    returns setof items
    language sql
as $$
    select
        items
    from
        items
        join item_categories on item_categories.item_id = items.id
        join categories on item_categories.category_id = categories.id
    where
        status = 'PUBLISHED' and
        (
            q_categories = '{}' or
            categories.name = any(q_categories)
        ) and (
            q_query = '' or
            lower(items.title) like lower(format('%%%s%%', q_query)) or
            lower(items.description) like lower(format('%%%s%%', q_query)) or
            lower(categories.name) like lower(format('%%%s%%', q_query))
        )
    group by
        items.id;
$$;


-- 2. создание объявление == insert

create or replace function insert_photo(q_data bytea, q_upload_datetime timestamp)
    returns bigint
    language plpgsql
as $$
    declare new_id bigint;
    begin
        select nextval('photos_id_seq') into new_id;
        insert into photos values (new_id, q_data, q_upload_datetime);
        return new_id;
    end;
$$;

create or replace function insert_item(q_title varchar(64), q_photo_id bigint, q_description varchar(1024),
                                       q_user_id bigint, q_creation_datetime timestamp)
    returns bigint
    language plpgsql
as $$
    declare new_id bigint;
    begin
        select nextval('items_id_seq') into new_id;
        insert into items values (new_id, q_title, q_photo_id, q_description, 'PUBLISHED', q_user_id, q_creation_datetime);
        insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id)
            values (new_id, 'NEW', 'PUBLISHED', q_creation_datetime, q_user_id);
        return new_id;
    end;
$$;


-- 3. бронирование объявления

create or replace procedure book_item(q_item_id bigint, q_user_id bigint)
    language plpgsql
as $$
    declare cur_status varchar(16); cur_user_id bigint; timestamp timestamp;
    begin
        select status, user_id from items where id = q_item_id into cur_status, cur_user_id;
        if cur_status != 'PUBLISHED' then
            raise exception 'item % must be PUBLISHED to book it', q_item_id;
        end if;
        if cur_user_id = q_user_id then
            raise exception 'user % cannot book his own item %', q_user_id, q_item_id;
        end if;
        select now() into timestamp;
        update items set status = 'BLOCKED' where id = q_item_id;
        insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id)
            values (q_item_id, 'PUBLISHED', 'BLOCKED', timestamp, q_user_id);
        insert into bookings (user_id, item_id, booking_datetime)
            values (q_user_id, q_item_id, timestamp);
    end;
$$;


-- 4. получение бронирований пользователя

create or replace function find_booked_items(q_user_id bigint)
    returns setof items
    language sql
as $$
    select
        items
    from
        bookings
        join items on bookings.item_id = items.id
    where
        bookings.user_id = q_user_id;
$$;


-- 5. закрытие объявления

create or replace procedure close_item(q_item_id bigint, q_user_id bigint)
    language plpgsql
as $$
    declare cur_status varchar(16); cur_user_id bigint;
    begin
        select status, user_id from items where id = q_item_id into cur_status, cur_user_id;
        if cur_status != 'BLOCKED' then
            raise exception 'item % must be BLOCKED to close it', q_item_id;
        end if;
        if cur_user_id != q_user_id then
            raise exception 'user % can close only his own items', q_user_id;
        end if;
        update items set status = 'ARCHIVED' where id = q_item_id;
        insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id)
            values (q_item_id, cur_status, 'ARCHIVED', now(), q_user_id);
        delete from bookings where item_id = q_item_id;
    end;
$$;


-- 6. добавление объявление в избранное == insert


-- 7. получение избранного пользователя

create or replace function find_saved_items(q_user_id bigint)
    returns setof items
    language sql
as $$
    select
        items
    from
        saved_items
        join items on saved_items.item_id = items.id
    where
        saved_items.user_id = q_user_id;
$$;


-- 8. создание подписки == insert

create or replace function insert_subscription(q_user_id bigint, q_name varchar(64), q_creation_datetime timestamp)
    returns bigint
    language plpgsql
as $$
    declare new_id bigint;
    begin
        select nextval('subscriptions_id_seq') into new_id;
        insert into subscriptions values (new_id, q_user_id, q_name, q_creation_datetime);
        return new_id;
    end;
$$;


-- 9. получение подписок пользователя

create or replace function find_subscriptions(q_user_id bigint)
    returns setof subscriptions
    language sql
as $$
    select
        *
    from
        subscriptions
    where
        subscriptions.user_id = q_user_id;
$$;


-- МЕТА. снятие бронирования

create or replace procedure cancel_booking(q_item_id bigint, q_user_id bigint)
    language plpgsql
as $$
declare cur_status varchar(16); cur_user_id bigint; cur_booked_user_id bigint;
begin
    select status, items.user_id, bookings.user_id
        from items left join bookings on bookings.item_id = items.id
        where items.id = q_item_id
        into cur_status, cur_user_id, cur_booked_user_id;
    if cur_status != 'BLOCKED' then
        raise exception 'item % must be BLOCKED to cancel it', q_item_id;
    end if;
    if cur_user_id != q_user_id and cur_booked_user_id != q_user_id then
        raise exception 'user % can cancel only his own bookings', q_user_id;
    end if;
    update items set status = 'PUBLISHED' where id = q_item_id;
    insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id)
    values (q_item_id, cur_status, 'PUBLISHED', now(), q_user_id);
    delete from bookings where item_id = q_item_id;
end;
$$;


-- МЕТА. получение сработанных подписок

create or replace function find_covering_subscriptions(q_categories text[])
    returns setof subscriptions
    language sql
as $$
    select
        subscriptions
    from
        subscriptions
        join subscription_categories on subscription_categories.subscription_id = subscriptions.id
        join categories on subscription_categories.category_id = categories.id
    where
        categories.name = any(q_categories)
    group by
        subscriptions.id
$$;
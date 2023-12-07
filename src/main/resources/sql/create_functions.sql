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


-- 3. бронирование объявления

create or replace procedure book_item(q_item_id bigint, q_user_id bigint)
    language plpgsql
as $$
    declare cur_status varchar(16); cur_user_id bigint; timestamp timestamp;
    begin
        select status, user_id from items where id = q_item_id into cur_status, cur_user_id;
        if cur_status != 'ACTIVE' then
            raise exception 'item % must be ACTIVE to book it', q_item_id;
        end if;
        if cur_user_id = q_user_id then
            raise exception 'user % cannot book his own item %', q_user_id, q_item_id;
        end if;
        select now() into timestamp;
        update items set status = 'BLOCKED' where id = q_item_id;
        insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id)
            values (q_item_id, 'ACTIVE', 'BLOCKED', timestamp, q_user_id);
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
        update items set status = 'CLOSED' where id = q_item_id;
        insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id)
            values (q_item_id, cur_status, 'CLOSED', now(), q_user_id);
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

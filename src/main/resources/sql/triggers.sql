-- проверка, что каждый пользователь имеет не более 3 бронирований

create or replace function check_user_bookings_limit()
returns trigger as $$
    begin
        if (select count(*) from bookings where user_id = new.user_id) >= 3 then
            raise exception 'user % already has 3 bookings', new.user_id;
        end if;
        return new;
    end;
$$
language plpgsql;

create or replace trigger user_booking_limiter
    before insert on bookings
    for each row execute function check_user_bookings_limit();


-- проверка, что каждое объявление имеет не более 5 категорий

create or replace function check_item_categories_limit()
returns trigger as $$
    begin
        if (select count(*) from item_categories where item_id = new.item_id) >= 5 then
            raise exception 'item % already has 5 categories', new.item_id;
        end if;
        return new;
    end;
$$
language plpgsql;

create or replace trigger item_categories_limiter
    before insert on item_categories
    for each row execute function check_item_categories_limit();


-- проверка, что каждая подписка имеет не более 5 категорий

create or replace function check_subscription_categories_limit()
returns trigger as $$
    begin
        if (select count(*) from subscription_categories where subscription_id = new.subscription_id) >= 5 then
            raise exception 'subscription % already has 5 categories', new.subscription_id;
        end if;
        return new;
    end;
$$
language plpgsql;

create or replace trigger subscription_categories_limiter
    before insert on subscription_categories
    for each row execute function check_subscription_categories_limit();

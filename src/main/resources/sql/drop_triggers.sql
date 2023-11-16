drop trigger if exists user_booking_limiter on bookings;
drop trigger if exists item_categories_limiter on item_categories;
drop trigger if exists subscription_categories_limiter on subscription_categories;

drop function if exists check_user_bookings_limit,
    check_item_categories_limit,
    check_subscription_categories_limit;
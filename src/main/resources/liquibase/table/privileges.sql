--liquibase formatted sql

--changeset andryss:create-privileges-table
create table privileges (
    id text primary key,
    privilege text
);

--changeset andryss:add-privileges
insert into privileges (id, privilege) values ('pages.home.view', 'VIEW_HOME_PAGE'),
                                              ('pages.profile.view', 'VIEW_PROFILE_PAGE'),
                                              ('pages.search.view', 'VIEW_SEARCH_PAGE'),
                                              ('public', 'PUBLIC'),
                                              ('pages.item.new.view', 'VIEW_NEW_ITEM_PAGE'),
                                              ('actions.item.create', 'CREATE_ITEM_ACTION'),
                                              ('actions.item.book', 'BOOK_ITEM_ACTION'),
                                              ('actions.item.save', 'SAVE_ITEM_ACTION'),
                                              ('actions.subscription.create', 'CREATE_SUBSCRIPTION_ACTION');

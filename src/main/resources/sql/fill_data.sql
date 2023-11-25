insert into dormitories (name, address) values
    ('Студенческий городок', 'Санкт-Петербург, пер. Вяземский, д. 5/7'),
    ('Общежитие №2', 'Санкт-Петербург, ул. Ленсовета, д. 23, лит. А'),
    ('Общежитие №3', 'Санкт-Петербург, Альпийский пер., д. 15, к. 2, лит. А'),
    ('Общежитие №4', 'Санкт-Петербург, ул. Белорусская, д. 6, лит. А');

insert into users (login, password, role, dormitory_id, room) values
    ('admin', 'password-hash', 'ADMIN', 1, '101'),
    ('user1', 'password-hash', 'USER', 1, '205'),
    ('user2', 'password-hash', 'USER', 1, '803'),
    ('user3', 'password-hash', 'USER', 1, '203');

-- TODO: научиться в относительные пути
insert into photos (data, upload_datetime, upload_user_id) values
    (pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/1.jpg'), '2023-09-01 10:00', 2),
    (pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/2.jpg'), '2023-09-01 10:30', 2),
    (pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/3.jpg'), '2023-09-02 13:00', 2),
    (pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/4.jpg'), '2023-09-03 15:00', 3),
    (pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/5.jpg'), '2023-09-03 16:00', 3);

insert into category_groups (name, description) values
    ('Мебель', 'Включает в себя все виды предметов интерьера, предназначенных для обеспечения комфорта и функциональности жилого пространства'),
    ('Техника', 'Включает в себя различные устройства и оборудование, которые используются в повседневной жизни для выполнения различных задач и упрощения процессов'),
    ('Одежда', 'Включает в себя все виды предметов, которые люди носят на своем теле для защиты от холода, жары, ветра и других внешних воздействий, а также для создания определенного образа и выражения индивидуальности'),
    ('Посуда', 'Включает в себя разнообразные предметы, используемые для приготовления, хранения, транспортировки и сервировки пищи'),
    ('Хозтовары', 'Включает в себя широкий ассортимент товаров, необходимых для поддержания чистоты, порядка и комфорта в доме'),
    ('Другое', 'Включает в себя все, что не подошло под другие группы категорий');

insert into categories (name, description, group_id) values
    ('Стул', '???', 1),
    ('Стол', '???', 1),
    ('Полка', '???', 1),
    ('Тумба', '???', 1),
    ('Провод', '???', 2),
    ('Зарядка', '???', 2),
    ('Внешний аккумулятор', '???', 2),
    ('Мышка', '???', 2),
    ('Внешний накопитель', '???', 2),
    ('Перчатки', '???', 3),
    ('Шапка', '???', 3),
    ('Нижнее белье', '???', 3),
    ('Носки', '???', 3),
    ('Тарелка', '???', 4),
    ('Вилка', '???', 4),
    ('Ложка', '???', 4),
    ('Кухонный нож', '???', 4),
    ('Тряпка', '???', 5),
    ('Гель для душа', '???', 5),
    ('Мыло', '???', 5),
    ('Зубная щетка', '???', 5),
    ('Другое', 'Все, что не подошло под другие категории', 6);

insert into items (title, photo_id, description, status, user_id, creation_datetime) values
    ('Провод переходник USB/Type-c', 1, '????????????????????', 'ACTIVE', 2, '2023-09-01 10:20'),
    ('Стул ортопедический на колесиках', 2, '????????????????????', 'ACTIVE', 2, '2023-09-01 10:50'),
    ('Набор шампуней и гелей для душа', 3, '????????????????????', 'BLOCKED', 2, '2023-09-02 13:20'),
    ('Шапка ушанка', 4, '????????????????????', 'ACTIVE', 3, '2023-09-03 15:20'),
    ('Трусы с волком', 5, '????????????????????', 'ACTIVE', 3, '2023-09-03 16:20');

insert into item_status_history (item_id, old_status, new_status, change_datetime, change_user_id) values
    (1, 'ARCHIVED', 'ACTIVE', '2023-09-01 10:21', 2),
    (2, 'ARCHIVED', 'ACTIVE', '2023-09-01 10:51', 2),
    (3, 'ARCHIVED', 'ACTIVE', '2023-09-02 13:21', 2),
    (4, 'ARCHIVED', 'ACTIVE', '2023-09-03 15:21', 3),
    (5, 'ARCHIVED', 'ACTIVE', '2023-09-03 16:21', 3),
    (3, 'ACTIVE', 'BLOCKED', '2023-09-03 18:00', 4);

insert into bookings (user_id, item_id, booking_datetime) values
    (4, 3, '2023-09-03 18:00');

insert into saved_items (user_id, item_id, added_datetime) values
    (4, 3, '2023-09-03 15:00'),
    (4, 4, '2023-09-03 15:05'),
    (2, 3, '2023-09-03 13:25'),
    (4, 5, '2023-09-04 15:05');

insert into subscriptions (user_id, name, created_datetime) values
    (4, 'Хочу гель или мыло для душа', '2023-09-01 15:10'),
    (4, 'Ищу что бы надеть', '2023-09-01 15:30'),
    (3, 'Что-нибудь на замену волко-трусам', '2023-09-03 16:30');

insert into item_categories (item_id, category_id) values
    (1, 5),
    (2, 1),
    (3, 19),
    (4, 11),
    (5, 12);

insert into subscription_categories (subscription_id, category_id) values
    (1, 19),
    (1, 20),
    (2, 10),
    (2, 11),
    (2, 12),
    (2, 13),
    (3, 12);

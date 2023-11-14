insert into dormitories values
    (1, 'Студенческий городок', 'Санкт-Петербург, пер. Вяземский, д. 5/7'),
    (2, 'Общежитие №2', 'Санкт-Петербург, ул. Ленсовета, д. 23, лит. А'),
    (3, 'Общежитие №3', 'Санкт-Петербург, Альпийский пер., д. 15, к. 2, лит. А'),
    (4, 'Общежитие №4', 'Санкт-Петербург, ул. Белорусская, д. 6, лит. А');

insert into users values
    (1, 'admin', 'password', 'ADMIN', 1, '101'),
    (2, 'user1', 'password', 'USER', 1, '205'),
    (3, 'user2', 'password', 'USER', 1, '803'),
    (4, 'user3', 'password', 'USER', 1, '203');

insert into photos values
    (1, pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/1.jpg'), '2023-09-01 10:00', 2),
    (2, pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/2.jpg'), '2023-09-01 10:30', 2),
    (3, pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/3.jpg'), '2023-09-02 13:00', 2),
    (4, pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/4.jpg'), '2023-09-03 15:00', 3),
    (5, pg_read_binary_file('/Users/andryssssss/Programming/Projects/Java/ITMO/Trousseau/src/main/resources/sql/img/5.jpg'), '2023-09-03 16:00', 3);

insert into category_groups values
    (1, 'Мебель', '???'),
    (2, 'Техника', '???'),
    (3, 'Одежда', '???'),
    (4, 'Посуда', '???'),
    (5, 'Хозтовары', '???'),
    (6, 'Другое', 'Все, что не подошло под другие группы категорий');

insert into categories values
    (1, 'Стул', '???', 1),
    (2, 'Стол', '???', 1),
    (3, 'Полка', '???', 1),
    (4, 'Тумба', '???', 1),
    (5, 'Провод', '???', 2),
    (6, 'Зарядка', '???', 2),
    (7, 'Внешний аккумулятор', '???', 2),
    (8, 'Мышка', '???', 2),
    (9, 'Внешний накопитель', '???', 2),
    (10, 'Перчатки', '???', 3),
    (11, 'Шапка', '???', 3),
    (12, 'Нижнее белье', '???', 3),
    (13, 'Носки', '???', 3),
    (14, 'Тарелка', '???', 4),
    (15, 'Вилка', '???', 4),
    (16, 'Ложка', '???', 4),
    (17, 'Кухонный нож', '???', 4),
    (18, 'Тряпка', '???', 5),
    (19, 'Гель для душа', '???', 5),
    (20, 'Мыло', '???', 5),
    (21, 'Зубная щетка', '???', 5),
    (22, 'Другое', 'Все, что не подошло под другие категории', 6);

insert into items values
    (1, 'Провод переходник USB/Type-c', 1, '???', 'ACTIVE', 2, '2023-09-01 10:20'),
    (2, 'Стул ортопедический на колесиках', 2, '???', 'ACTIVE', 2, '2023-09-01 10:50'),
    (3, 'Набор шампуней и гелей для душа', 3, '???', 'ACTIVE', 2, '2023-09-02 13:20'),
    (4, 'Шапка ушанка', 4, '???', 'BLOCKED', 3, '2023-09-03 15:20'),
    (5, 'Трусы с волком', 5, '???', 'ACTIVE', 3, '2023-09-03 16:20');

insert into item_status_history values
    (1, 1, 'ARCHIVED', 'ACTIVE', '2023-09-01 10:21', 2),
    (2, 2, 'ARCHIVED', 'ACTIVE', '2023-09-01 10:51', 2),
    (3, 3, 'ARCHIVED', 'ACTIVE', '2023-09-02 13:21', 2),
    (4, 4, 'ARCHIVED', 'ACTIVE', '2023-09-03 15:21', 3),
    (5, 5, 'ARCHIVED', 'ACTIVE', '2023-09-03 16:21', 3),
    (6, 3, 'ACTIVE', 'BLOCKED', '2023-09-03 18:00', 4);

insert into bookings values
    (1, 4, 3, '2023-09-03 18:00');

insert into saved_items values
    (4, 3, '2023-09-03 15:00'),
    (4, 4, '2023-09-03 15:05'),
    (2, 3, '2023-09-03 13:25'),
    (4, 5, '2023-09-04 15:05');

insert into subscriptions values
    (1, 4, 'Хочу гель или мыло для душа', '2023-09-01 15:10'),
    (2, 4, 'Ищу что бы надеть', '2023-09-01 15:30'),
    (3, 3, 'Что-нибудь на замену волко-трусам', '2023-09-03 16:30');

insert into item_categories values
    (1, 5),
    (2, 1),
    (3, 19),
    (4, 11),
    (5, 12);

insert into subscription_categories values
    (1, 19),
    (1, 20),
    (2, 10),
    (2, 11),
    (2, 12),
    (2, 13),
    (3, 12);

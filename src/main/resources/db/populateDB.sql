DELETE FROM vote;
DELETE FROM user_roles;
DELETE FROM user;
DELETE FROM dish;
DELETE FROM menu;
DELETE FROM rest;
-- ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO rest (name)
VALUES ('Restaurant 1'),
       ('Restaurant 2');

INSERT INTO menu (rest_id, date)
VALUES (100000, '2020-08-26'), --2
       (100000, '2020-08-27'),
       (100001, '2020-08-26'),
       (100001, '2020-08-27'); --5

INSERT INTO dish (menu_id, name, price)
VALUES (100002, 'Борщ', 10000), --6
       (100002, 'Хлеб', 11500),
       (100003, 'Картошка', 14500),
       (100003, 'Котлета', 9000),
       (100003, 'Салат', 16000),
       (100004, 'Уха', 12000),
       (100004, 'Макароны', 11500),
       (100004, 'Пельмени', 13500),
       (100005, 'Борщ', 10500),
       (100005, 'Сосиски', 11000); --15

INSERT INTO user (name)
VALUES ('Admin'), --16
       ('User1'),
       ('User1'),
       ('User3'); --19

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 100016),
       ('USER', 100016),
       ('USER', 100017),
       ('USER', 100018),
       ('USER', 100019);

INSERT INTO vote (rest_id, user_id, date)
VALUES (100000, 100017, '2020-08-26'),
       (100001, 100018, '2020-08-26'),
       (100000, 100019, '2020-08-26'),
       (100001, 100017, '2020-08-27'),
       (100000, 100018, '2020-08-27'),
       (100001, 100019, '2020-08-27');
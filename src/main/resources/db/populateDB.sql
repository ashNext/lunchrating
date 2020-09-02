DELETE FROM vote;
DELETE FROM user_roles;
DELETE FROM user;
DELETE FROM dish;
DELETE FROM menu;
DELETE FROM rest;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO rest (name)
VALUES ('Restaurant 1'),
       ('Restaurant 2');

INSERT INTO menu (rest_id, date)
VALUES (100000, DATE_SUB(NOW(), 1 DAY )), --2
       (100000, NOW()),
       (100001, DATE_SUB(NOW(), 1 DAY )),
       (100001, NOW()); --5

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

INSERT INTO user (name, password)
VALUES ('Admin', 'admin'), --16
       ('User1', 'user1'),
       ('User2', 'user2'),
       ('User3', 'user3'); --19

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 100016),
       ('USER', 100016),
       ('USER', 100017),
       ('USER', 100018),
       ('USER', 100019);

INSERT INTO vote (rest_id, user_id, date)
VALUES (100000, 100017, DATE_SUB(NOW(), 1 DAY )),
       (100001, 100018, DATE_SUB(NOW(), 1 DAY )),
       (100000, 100019, DATE_SUB(NOW(), 1 DAY )),
       (100001, 100017, NOW()),
       (100000, 100018, NOW()),
       (100001, 100019, NOW());
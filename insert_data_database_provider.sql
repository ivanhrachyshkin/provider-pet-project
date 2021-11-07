ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT ALL ON TABLES TO provider;

------------------------------------------------------------------------------------------------------------------------
-- Tables
------------------------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS traffics;
DROP TABLE IF EXISTS promotions;
DROP TABLE IF EXISTS subscriptions;
DROP TABLE IF EXISTS tariffs;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS accounts;

-- Accounts
CREATE TABLE accounts
(
    id       SERIAL         NOT NULL PRIMARY KEY,
    email    CHARACTER VARYING (255) NOT NULL,
    password CHARACTER VARYING(64)  NOT NULL,
    role     INTEGER        NOT NULL,
    name     CHARACTER VARYING(70)  NOT NULL,
    phone    CHARACTER VARYING(35)  NOT NULL,
    address  CHARACTER VARYING(255) NOT NULL,
    balance  REAL           NOT NULL,
    UNIQUE (email)
);

-- Discounts

CREATE TABLE discounts
(
    id        SERIAL PRIMARY KEY,
    name      CHARACTER VARYING(70) NOT NULL,
    type      INTEGER       NOT NULL,
    value     INTEGER       NOT NULL,
    date_from DATE          NOT NULL,
    date_to   DATE          NOT NULL,
    UNIQUE (name)
);

-- Tariffs

CREATE TABLE tariffs
(
    id    SERIAL PRIMARY KEY,
    name  CHARACTER VARYING(70) NOT NULL,
    type  INTEGER       NOT NULL,
    speed INTEGER       NOT NULL,
    price REAL          NOT NULL,
    UNIQUE (name)
);

-- Subscriptions

CREATE TABLE subscriptions
(
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL REFERENCES accounts (id),
    tariff_id  INTEGER NOT NULL REFERENCES tariffs (id),
    UNIQUE (account_id, tariff_id)
);


-- Promotions

CREATE TABLE promotions
(
    tariff_id   INTEGER NOT NULL REFERENCES tariffs (id),
    discount_id INTEGER NOT NULL REFERENCES discounts (id),
    UNIQUE (tariff_id, discount_id)
);

-- Traffics

CREATE TABLE traffics
(
    subscription_id INTEGER  NOT NULL REFERENCES subscriptions (id),
    value           INTEGER NOT NULL,
    date            DATE    NOT NULL
);

-- Bills

CREATE TABLE bills
(
    subscription_id INTEGER  NOT NULL REFERENCES subscriptions (id),
    value           INTEGER NOT NULL,
    date            DATE    NOT NULL,
    status          BOOLEAN NOT NULL
);

INSERT
INTO accounts (email, password, role, name, phone, address, balance)
VALUES ('o@outlook.com',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1,
        'Jack White',
        '+1-993-631-7614',
        '1619 New Hampshire Avenue., N.W. Washington, DC 20009',
        0.0),
       ('hr6zdl@yandex.ru',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Edgar Allan Poe',
        '+1-727-456-7504',
        '321 Mia Field Apt. 340 New Lilianfort, NE 72310',
        500.0),
       ('kaft93x@outlook.com',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Charles Palahniuk',
        '+1-914-733-7350',
        '60441 Piper Inlet Wilfredton, GA 46024',
        9500.0),
       ('dcu@yandex.ru',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Richard David Bach',
        '+1-940-969-0301',
        '144 O''Connell Courts Apt. 889 Lake Zoe, IL 39853',
        1010.0),
       ('19dn@outlook.com',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Charles Dickens',
        '+1-625-541-3328',
        '269 Abshire Gardens Suite 144 Kutchfort, IL 80951',
        10000.0),
       ('pa5h@mail.ru',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Mark Twain',
        '+1-672-629-1986',
        '15744 Trantow Cove Suite 349 East Darrel, NH 82680',
        6150.0),
       ('281av0@gmail.com',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Ernest Hemingway',
        '+1-227-876-8006',
        '33476 Pablo Turnpike Port Lorishire, WY 23162',
        98.0),
       ('8edmfh@outlook.com',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Friedrich Nietzsche',
        '+1-591-886-3760',
        '447 Carli Lodge Lake Vella, NM 40534',
        7450.0);

INSERT
INTO discounts (name, type, value, date_from, date_to)
VALUES ('Better Try', 0, 10, '2021-11-04', '2021-11-30'),
       ('Super duper', 1, 50, '2021-12-04', '2021-12-30'),
       ('Winter Is Coming', 0, 30, '2021-11-01', '2022-02-28');

INSERT
INTO tariffs (name, type, speed, price)
VALUES ('Home 3', 0, 3000, 10.0),
       ('Work 6', 0, 6000, 15.0),
       ('Relax 5', 1, 5000, 15.0),
       ('Relax 2', 1, 2000, 13.0),
       ('Sonic', 0, 10000, 50.0);

INSERT
INTO subscriptions (account_id, tariff_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (4, 4),
       (5, 5),
       (6, 2),
       (7, 1),
       (7, 4);

INSERT
INTO promotions (tariff_id, discount_id)
VALUES (1, 1),
       (2, 1),
       (3, 3),
       (4, 3),
       (5, 2);

INSERT
INTO traffics (subscription_id, value, date)
VALUES (1, 10, '2021-10-01'),
       (3, 10, '2021-10-01'),
       (2, 10, '2021-10-01'),
       (4, 10, '2021-10-01'),
       (5, 10, '2021-10-01'),
       (7, 10, '2021-10-01'),
       (6, 10, '2021-10-01'),
       (2, 20, '2021-10-02'),
       (3, 20, '2021-10-02'),
       (7, 20, '2021-10-02'),
       (6, 20, '2021-10-02'),
       (1, 20, '2021-10-02'),
       (4, 20, '2021-10-02'),
       (5, 20, '2021-10-02'),
       (1, 30, '2021-10-03'),
       (2, 30, '2021-10-03'),
       (5, 30, '2021-10-03'),
       (3, 30, '2021-10-03'),
       (4, 30, '2021-10-03'),
       (6, 30, '2021-10-03'),
       (7, 30, '2021-10-03'),
       (1, 30, '2021-10-04'),
       (2, 30, '2021-10-04'),
       (4, 40, '2021-10-04'),
       (5, 40, '2021-10-04'),
       (9, 30, '2021-10-04'),
       (6, 40, '2021-10-04'),
       (7, 40, '2021-10-04'),
       (1, 40, '2021-10-05'),
       (2, 40, '2021-10-05'),
       (3, 40, '2021-10-05'),
       (4, 40, '2021-10-05'),
       (5, 40, '2021-10-05'),
       (6, 40, '2021-10-05'),
       (9, 40, '2021-10-05');


INSERT
INTO bills (subscription_id, value, date, status)
VALUES (1, 4, '2021-10-01', false),
       (1, 5, '2021-10-02', true),
       (1, 7, '2021-10-03', false),
       (1, 2, '2021-10-04', false),
       (1, 8, '2021-10-05', true),
       (2, 2, '2021-10-01', false),
       (2, 4, '2021-10-02', true),
       (2, 3, '2021-10-03', false),
       (2, 6, '2021-10-04', false),
       (2, 8, '2021-10-05', true),
       (3, 10, '2021-10-01', true),
       (3, 2, '2021-10-02', true),
       (3, 8, '2021-10-03', false),
       (3, 3, '2021-10-04', true),
       (3, 8, '2021-10-05', false),
       (4, 6, '2021-10-01', true),
       (4, 20, '2021-10-02', true),
       (4, 30, '2021-10-03', true),
       (4, 4, '2021-10-04', true),
       (4, 1, '2021-10-05', true),
       (5, 11, '2021-10-01', false),
       (5, 20, '2021-10-02', false),
       (5, 30, '2021-10-03', false),
       (5, 4, '2021-10-04', false),
       (5, 40, '2021-10-05', true),
       (6, 10, '2021-10-01', false),
       (6, 2, '2021-10-02', false),
       (6, 30, '2021-10-03', true),
       (6, 40, '2021-10-04', false),
       (6, 40, '2021-10-05', false),
       (7, 1, '2021-10-01', false),
       (7, 20, '2021-10-02', true),
       (7, 3, '2021-10-03', false),
       (7, 40, '2021-10-04', false),
       (7, 40, '2021-10-05', true),
       (8, 90, '2021-10-01', false),
       (8, 20, '2021-10-02', false),
       (8, 9, '2021-10-03', false),
       (8, 0, '2021-10-04', false),
       (8, 40, '2021-10-05', true),
       (9, 10, '2021-10-01', true),
       (9, 20, '2021-10-02', true),
       (9, 30, '2021-10-03', true),
       (9, 40, '2021-10-04', true),
       (9, 2, '2021-10-05', false);

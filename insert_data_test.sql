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
    id       SERIAL                 NOT NULL PRIMARY KEY,
    email    CHARACTER VARYING(255) NOT NULL,
    password CHARACTER VARYING(64)  NOT NULL,
    role     INTEGER                NOT NULL,
    name     CHARACTER VARYING(70)  NOT NULL,
    phone    CHARACTER VARYING(35)  NOT NULL,
    address  CHARACTER VARYING(255) NOT NULL,
    balance  REAL                   NOT NULL,
    UNIQUE (email)
);

-- Discounts

CREATE TABLE discounts
(
    id        SERIAL PRIMARY KEY,
    name      CHARACTER VARYING(70) NOT NULL,
    type      INTEGER               NOT NULL,
    value     INTEGER               NOT NULL,
    date_from DATE                  NOT NULL,
    date_to   DATE                  NOT NULL,
    UNIQUE (name)
);

-- Tariffs

CREATE TABLE tariffs
(
    id    SERIAL PRIMARY KEY,
    name  CHARACTER VARYING(70) NOT NULL,
    type  INTEGER               NOT NULL,
    speed INTEGER               NOT NULL,
    price REAL                  NOT NULL,
    UNIQUE (name)
);

-- Subscriptions

CREATE TABLE subscriptions
(
    id         SERIAL PRIMARY KEY,
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
    subscription_id INTEGER NOT NULL REFERENCES subscriptions (id),
    value           INTEGER NOT NULL,
    date            DATE    NOT NULL
);

-- Bills

CREATE TABLE bills
(
    subscription_id INTEGER NOT NULL REFERENCES subscriptions (id),
    value           INTEGER NOT NULL,
    date            DATE    NOT NULL,
    status          BOOLEAN NOT NULL
);

INSERT
INTO accounts (email, password, role, name, phone, address, balance)
VALUES ('a@outlook.com',
        'ee11cbb19052e40b07aac0ca060c23ee', 0,
        'Jack White',
        '+1-993-631-7614',
        '1619 New Hampshire Avenue., N.W. Washington, DC 20009',
        0.0),
       ('b@outlook.com',
        'ee11cbb19052e40b07aac0ca060c23ee',
        1,
        'Edgar Allan Poe',
        '+1-727-456-7504',
        '321 Mia Field Apt. 340 New Lilianfort, NE 72310',
        500.0),
       ('c@outlook.com',
        'ee11cbb19052e40b07aac0ca060c23ee',
        2,
        'Charles Palahniuk',
        '+1-914-733-7350',
        '60441 Piper Inlet Wilfredton, GA 46024',
        9500.0);

INSERT
INTO discounts (name, type, value, date_from, date_to)
VALUES ('Winter Is Coming', 0, 30, '2021-12-01', '2022-02-27'),
       ('Summer Is Coming', 0, 20, '2021-06-01', '2022-08-30'),
       ('Spring Is Coming', 0, 40, '2021-03-01', '2022-05-30');

INSERT
INTO tariffs (name, type, speed, price)
VALUES ('Home 3', 0, 3000, 10.0),
       ('Work 6', 0, 6000, 15.0),
       ('Relax 5', 1, 5000, 15.0);
INSERT
INTO subscriptions (account_id, tariff_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (3, 3);

INSERT
INTO promotions (tariff_id, discount_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 1),
       (3, 2);

INSERT
INTO traffics (subscription_id, value, date)
VALUES (1, 111, '2021-4-01'),
    (1, 112, '2021-4-04'),
    (1, 113, '2021-4-08'),
    (2, 221, '2021-1-01'),
    (2, 222, '2021-2-01'),
    (2, 223, '2021-4-16'),
    (3, 331, '2021-12-01'),
    (3, 332, '2021-2-11'),
    (3, 333, '2021-4-20');


INSERT
INTO bills (subscription_id, value, date, status)
VALUES (1, 4, '2021-11-01', false),
       (1, 5, '2021-4-02', false),
       (1, 7, '2021-12-03', false),
       (2, 2, '2021-4-01', false),
       (2, 4, '2021-2-02', false),
       (2, 3, '2021-12-03', false),
       (3, 6, '2021-4-04', false),
       (3, 8, '2021-3-05', false),
       (3, 8, '2021-3-05', false);

GRANT
USAGE,
SELECT
ON ALL SEQUENCES IN SCHEMA public TO provider;
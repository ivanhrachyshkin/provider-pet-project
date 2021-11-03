------------------------------------------------------------------------------------------------------------------------
-- Database
------------------------------------------------------------------------------------------------------------------------
// разбить на 2 и в корень, избавться от схема дао и листенери датасурс с3по контекст удалить
DROP
    DATABASE IF EXISTS provider;

CREATE
    DATABASE provider;

------------------------------------------------------------------------------------------------------------------------
-- User
------------------------------------------------------------------------------------------------------------------------

DROP
    USER IF EXISTS provider;

CREATE
    USER provider WITH ENCRYPTED PASSWORD 'provider';

GRANT ALL PRIVILEGES ON DATABASE
    provider TO provider;

------------------------------------------------------------------------------------------------------------------------
-- Tables
------------------------------------------------------------------------------------------------------------------------

-- Discounts

DROP TABLE IF EXISTS discounts;

CREATE TABLE discounts
(
    id        SERIAL PRIMARY KEY,
    name      CHARACTER(70) NOT NULL,
    type      INTEGER       NOT NULL,
    value     INTEGER       NOT NULL,
    date_from DATE          NOT NULL,
    date_to   DATE          NOT NULL,
    UNIQUE (name)
);

-- Tariffs

DROP TABLE IF EXISTS tariffs;

CREATE TABLE tariffs
(
    id    SERIAL PRIMARY KEY,
    name  CHARACTER(70) NOT NULL,
    type  INTEGER       NOT NULL,
    speed INTEGER       NOT NULL,
    price REAL          NOT NULL,
    UNIQUE (name)
);

-- Promotions

DROP TABLE IF EXISTS promotions;

CREATE TABLE promotions
(
    tariff_id   INTEGER NOT NULL REFERENCES tariffs (id),
    discount_id INTEGER NOT NULL REFERENCES discounts (id),
    UNIQUE (tariff_id, discount_id)
);

-- Accounts

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts
(
    id       SERIAL         NOT NULL PRIMARY KEY,
    email    CHARACTER(255) NOT NULL,
    password CHARACTER(64)  NOT NULL,
    role     INTEGER        NOT NULL,
    name     CHARACTER(70)  NOT NULL,
    phone    CHARACTER(35)  NOT NULL,
    address  CHARACTER(255) NOT NULL,
    balance  REAL           NOT NULL,
    UNIQUE (email)
);

-- Subscriptions

DROP TABLE IF EXISTS subscriptions;

CREATE TABLE subscriptions
(
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL REFERENCES accounts (id),
    tariff_id  INTEGER NOT NULL REFERENCES tariffs (id),
    UNIQUE (account_id, tariff_id)
);

-- Traffics

DROP TABLE IF EXISTS traffics;

CREATE TABLE traffics
(
    subscription_id INTEGER  NOT NULL REFERENCES subscriptions (id),
    value           INTEGER NOT NULL,
    date            DATE    NOT NULL
);

-- Bills

DROP TABLE IF EXISTS bills;

CREATE TABLE bills
(
    id              SERIAL  NOT NULL PRIMARY KEY,
    subscription_id INTEGER  NOT NULL REFERENCES accounts (id),
    value           INTEGER NOT NULL,
    date            DATE    NOT NULL,
    status          BOOLEAN NOT NULL
);

------------------------------------------------------------------------------------------------------------------------
-- Data
------------------------------------------------------------------------------------------------------------------------

INSERT
INTO accounts (email, password, role, name, phone, address, balance)
VALUES ('admin',
        '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        1,
        'Administrator',
        '+12029861805',
        '1619 New Hampshire Avenue., N.W. Washington, DC 20009',
        100.0)

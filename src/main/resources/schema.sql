BEGIN;

CREATE TABLE IF NOT EXISTS discounts
(
    id    SERIAL PRIMARY KEY,
    name  CHARACTER VARYING(35) NOT NULL UNIQUE,
    type  INTEGER               NOT NULL,
    value INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS tariffs
(
    id    SERIAL PRIMARY KEY,
    name  CHARACTER VARYING(70) NOT NULL UNIQUE,
    type  INTEGER               NOT NULL,
    speed INTEGER               NOT NULL,
    price DOUBLE PRECISION      NOT NULL
);

CREATE TABLE IF NOT EXISTS promotions
(
    id          SERIAL PRIMARY KEY,
    tariff_id   INTEGER REFERENCES tariffs (id),
    discount_id INTEGER REFERENCES discounts (id),
    date_from   DATE,
    date_to     DATE
);

CREATE TABLE IF NOT EXISTS accounts
(
    id       SERIAL PRIMARY KEY,
    name     CHARACTER VARYING(70)  NOT NULL UNIQUE,
    email    CHARACTER VARYING(255) NOT NULL UNIQUE,
    password CHARACTER VARYING(64)  NOT NULL,
    phone    INTEGER                NOT NULL,
    role     INTEGER                NOT NULL,
    balance  DOUBLE PRECISION       NOT NULL
);

CREATE TABLE IF NOT EXISTS traffics
(
    id              SERIAL PRIMARY KEY,
    subscription_id INTEGER,
    value           DOUBLE PRECISION,
    date            DATE
);

CREATE TABLE IF NOT EXISTS subscriptions
(
    id         SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts (id),
    tariff_id  INTEGER REFERENCES tariffs (id),
    date_from  DATE,
    date_to    DATE
);

CREATE TABLE IF NOT EXISTS bills
(
    id              SERIAL PRIMARY KEY,
    subscription_id INTEGER REFERENCES subscriptions (id),
    sum             DOUBLE PRECISION,
    date            DATE,
    status          BOOLEAN DEFAULT FALSE
);


INSERT INTO discounts (name, type, value)
VALUES ('', 1, 0)
ON CONFLICT DO NOTHING;

INSERT INTO tariffs (name, type, speed, price)
VALUES ('', 1, 0, 0)
ON CONFLICT DO NOTHING;

INSERT INTO promotions (tariff_id, discount_id, date_from, date_to)
VALUES (1, 1, '2000-10-10', '2000-10-10')
ON CONFLICT DO NOTHING;

INSERT INTO accounts (name, email, password, phone, role, balance)
VALUES ('Admin', '@admin', '21232f297a57a5a743894a0e4a801fc3', 1, 1, 0)
ON CONFLICT DO NOTHING;

INSERT INTO traffics (subscription_id, value, date)
VALUES (1, 1.0, '2000-10-10')
ON CONFLICT DO NOTHING;

INSERT INTO subscriptions (account_id, tariff_id, date_from, date_to)
VALUES (1, 1, '2000-10-10', '2000-10-10')
ON CONFLICT DO NOTHING;

INSERT INTO bills (subscription_id, sum, status)
VALUES (1, 1.0, false)
ON CONFLICT DO NOTHING;

END;
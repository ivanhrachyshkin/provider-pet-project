BEGIN;

CREATE TABLE IF NOT EXISTS promotion
(
    id               SERIAL PRIMARY KEY,
    name             CHARACTER VARYING(35) NOT NULL UNIQUE,
    description      TEXT                  NOT NULL,
    discount_percent INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS tariffs
(
    id               SERIAL PRIMARY KEY,
    name             CHARACTER VARYING(35) NOT NULL UNIQUE,
    type             INTEGER               NOT NULL,
    speed            INTEGER          DEFAULT 0,
    traffic_capacity INTEGER          DEFAULT 0,
    price            DOUBLE PRECISION DEFAULT 0,
    promotion_id     BIGINT                NOT NULL REFERENCES promotion (id)
);

CREATE TABLE IF NOT EXISTS balances
(
    id                SERIAL PRIMARY KEY,
    bill              DOUBLE PRECISION DEFAULT 0,
    last_deposit_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    traffic_quantity  INTEGER          DEFAULT 0
);

CREATE TABLE IF NOT EXISTS profiles
(
    id              SERIAL PRIMARY KEY,
    name            CHARACTER VARYING(35)  NOT NULL,
    surname         CHARACTER VARYING(35)  NOT NULL,
    email           CHARACTER VARYING(255) NOT NULL,
    identity_number CHARACTER VARYING(15)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS accounts
(
    id         SERIAL PRIMARY KEY,
    login      CHARACTER VARYING(256) NOT NULL UNIQUE,
    password   CHARACTER VARYING(32)  NOT NULL,
    role       INTEGER                NOT NULL,
    profile_id BIGINT                 NOT NULL REFERENCES profiles (id),
    tariff_id  BIGINT                 NOT NULL REFERENCES tariffs (id),
    balance_id BIGINT                 NOT NULL REFERENCES balances (id),
    blocked    BOOLEAN DEFAULT FALSE
);

INSERT INTO promotion (id, name, description, discount_percent)
VALUES (1, '', '', 0)
ON CONFLICT DO NOTHING;

INSERT INTO tariffs (id, name, type, speed, traffic_capacity, price, promotion_id)
VALUES (1, '', 1, 0, 0, 0, 1)
ON CONFLICT DO NOTHING;

INSERT INTO balances (id, bill, last_deposit_date, traffic_quantity)
VALUES (1, 0, NOW(), 0)
ON CONFLICT DO NOTHING;

INSERT INTO profiles (id, name, surname, email, identity_number)
VALUES (1, 'Admin', 'Admin', 'admin@gmail.com', '12345678')
ON CONFLICT DO NOTHING;

INSERT INTO accounts (id, login, password, role, profile_id, tariff_id, balance_id)
VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1, 1, 1, 1)
ON CONFLICT DO NOTHING;

END;
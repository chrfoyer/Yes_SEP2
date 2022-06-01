DROP SCHEMA IF EXISTS game_test CASCADE;

CREATE SCHEMA IF NOT EXISTS game_test;

SET SCHEMA 'game_test';

DROP TABLE IF EXISTS games CASCADE;

CREATE TABLE IF NOT EXISTS games
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(60) NOT NULL,
    producer     VARCHAR(40) NOT NULL,
    console      VARCHAR(15) NOT NULL,
    rented       BOOLEAN     NOT NULL,
    days_left    INT         NOT NULL,
    review_count INT         NOT NULL,
    review_sum   INT         NOT NULL,
    review_avg   FLOAT       NOT NULL,
    esrb         VARCHAR(5)  NOT NULL,
    date_added   DATE        NOT NULL
);

INSERT INTO games
(name, producer, console, rented, days_left, review_count, review_sum, review_avg, esrb, date_added)
VALUES ('Minecraft', 'Mojang', 'PC', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE),
       ('Rooster and Gall', 'Duty Toot', 'Xbox', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE),
       ('Call of Duty', 'Activision', 'Xbox', FALSE, 0, 0, 0, 3.0, 'T', CURRENT_DATE),
       ('Call of Duty', 'Activision', 'PC', FALSE, 0, 0, 0, 3.0, 'T', CURRENT_DATE),
       ('Call of Duty', 'Activision', 'PlayStation', FALSE, 0, 0, 0, 3.0, 'T', CURRENT_DATE),
       ('DOOM', 'id Software', 'PC', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Apex Legends', 'Respawn Entertainment', 'PC', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Apex Legends', 'Respawn Entertainment', 'Xbox', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Apex Legends', 'Respawn Entertainment', 'PlayStation', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Valheim', 'Iron Gate AB', 'PC', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('SEP2 Suffering Protocol: The Ballad of Misery', 'Group 3Y: yes', 'PC', FALSE, 0, 0, 0, 100, 'E', '4-20-2022'),
       ('Salty Tears: GitHub Edition', 'GitHub Studios', 'PC', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE);

CREATE DOMAIN email AS VARCHAR(40) CHECK (VALUE LIKE '%@%');
CREATE DOMAIN passwordDom AS VARCHAR(256); /* Here in case we want to change the rules */
CREATE DOMAIN bDay AS DATE CHECK (EXTRACT(YEAR FROM AGE(VALUE)) >= 13);

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    username         VARCHAR(30) PRIMARY KEY,
    password         passwordDom NOT NULL,
    is_admin         BOOLEAN,
    email            email,
    address          VARCHAR(100),
    name             VARCHAR(30),
    bDay             bDay,
    has_subscription BOOLEAN,
    balance          INT,
    age              INT,
    salt             VARCHAR
);

DROP TABLE IF EXISTS transactions CASCADE;
CREATE TABLE transactions
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    action   VARCHAR(50) NOT NULL,
    date     DATE,

    FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE rentals
(
    id                    SERIAL PRIMARY KEY,
    game_id               INT         NOT NULL,
    username              VARCHAR(30) NOT NULL,
    date_rented           DATE        NOT NULL,
    date_returned         DATE,
    rental_length_allowed INT         NOT NULL,
    days_left             INT,
    active                BOOLEAN     NOT NULL,
    overdue               BOOLEAN /* TODO extract from daysLeft */,

    FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE,
    FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

DROP FUNCTION IF EXISTS calculate_ages() CASCADE;
DROP TRIGGER IF EXISTS age_check
    ON users;
DROP FUNCTION IF EXISTS days_left() CASCADE;
DROP TRIGGER IF EXISTS days_refresh
    ON rentals;

CREATE FUNCTION calculate_ages()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF PG_TRIGGER_DEPTH() <> 1 THEN
        RETURN NEW;
    END IF;

    UPDATE users
    SET age = EXTRACT(YEAR FROM AGE(bDay))
    WHERE is_admin IS NOT TRUE;
    RETURN NEW;
END;
$$;

CREATE TRIGGER age_check
    AFTER INSERT OR UPDATE
    ON users
    FOR EACH STATEMENT
EXECUTE PROCEDURE calculate_ages();

CREATE FUNCTION days_left()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF PG_TRIGGER_DEPTH() <> 1 THEN
        RETURN NEW;
    END IF;

    UPDATE rentals
    SET days_left = rental_length_allowed - (CURRENT_DATE - rentals.date_rented)
    WHERE active = TRUE;

    RETURN NEW;
END;
$$;


CREATE TRIGGER days_refresh
    AFTER INSERT OR UPDATE
    ON rentals
    FOR EACH STATEMENT
EXECUTE PROCEDURE days_left();
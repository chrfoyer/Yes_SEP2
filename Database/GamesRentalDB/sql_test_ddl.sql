DROP SCHEMA IF EXISTS game_test CASCADE;

CREATE SCHEMA IF NOT EXISTS game_test;

SET SCHEMA 'game_test';

DROP TABLE IF EXISTS games CASCADE;

CREATE TABLE IF NOT EXISTS games
(
    id           SERIAL PRIMARY KEY,
    name         varchar(60) NOT NULL,
    producer     varchar(40) NOT NULL,
    console      varchar(15) NOT NULL,
    rented       boolean     NOT NULL, /* TODO obtain boolean with trigger?*/
    days_left    int         NOT NULL, /* TODO respect normalization by moving to rental */
    review_count int         NOT NULL,
    review_sum   int         NOT NULL,
    review_avg   float       NOT NULL, /* TODO obtain through trigger aggregating game */
    esrb         VARCHAR(5)  NOT NULL,
    date_added   date        NOT NULL
);

INSERT INTO games
(name, producer, console, rented, days_left, review_count, review_sum, review_avg, esrb, date_added)
VALUES ('Minecraft', 'Mojang', 'PC', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE),
       ('Rooster and Gall', 'Duty Toot', 'Xbox', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE);

CREATE DOMAIN email AS varchar(40) CHECK (VALUE LIKE '%@%');
CREATE DOMAIN passwordDom AS varchar(30); /* Here in case we want to change the rules */
CREATE DOMAIN bDay AS date CHECK (13 >= DATE_PART('year', CURRENT_DATE) - DATE_PART('year', value));

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    username         varchar(30) PRIMARY KEY,
    password         passwordDom NOT NULL,
    is_admin         boolean,
    email            email,
    address          varchar(100),
    name             varchar(30),
    bDay             bDay,
    has_subscription boolean,
    balance          int, /* TODO Plz change */
    age              int /* TODO extract value using date diff */
);

/*  -- DEPRECATED --

CREATE DOMAIN reviewValue AS int CHECK (VALUE BETWEEN 1 AND 5);

CREATE TABLE IF NOT EXISTS reviews
(
    id       SERIAL PRIMARY KEY,
    gameId   int         NOT NULL,
    username varchar(30) NOT NULL,
    value    reviewValue NOT NULL,

    FOREIGN KEY (gameId) REFERENCES games (id),
    FOREIGN KEY (username) REFERENCES users (username)
);


 */
CREATE TABLE transactions
(
    id       SERIAL PRIMARY KEY,
    username varchar(30) NOT NULL,
    amount   int         NOT NULL,
    type     varchar(15),
    date     date,

    FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE rentals
(
    id                    SERIAL PRIMARY KEY,
    game_id               int         NOT NULL,
    username              varchar(30) NOT NULL,
    date_rented           date        NOT NULL,
    date_returned         date,
    rental_length_allowed int         NOT NULL,
    days_left             int,
    active                boolean     NOT NULL /* TODO extract value using trigger */,
    overdue               boolean /* TODO extract from daysLeft */,

    FOREIGN KEY (game_id) REFERENCES games (id),
    FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO users
    (username, password)
VALUES ('admin', 'admin'),
       ('bob', 'test');

UPDATE users
SET is_admin= TRUE
WHERE username = 'admin';

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
    UPDATE users
    SET age = EXTRACT(YEAR FROM CURRENT_DATE - bDay);
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
    SET days_left = CURRENT_DATE - rentals.date_rented + rental_length_allowed
    WHERE active = TRUE;
    RETURN NEW;
END;
$$;


CREATE TRIGGER days_refresh
    AFTER INSERT OR UPDATE
    ON rentals
    FOR EACH STATEMENT
EXECUTE PROCEDURE days_left();

/*

DROP FUNCTION IF EXISTS wow();

CREATE FUNCTION wow()
    RETURNS INTEGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    diff integer;
BEGIN
    diff = CURRENT_DATE - '2022-05-10';
    RETURN diff;
END;
$$;

SELECT wow();

 */

UPDATE rentals
SET date_rented = CURRENT_DATE
WHERE id = 14;

SELECT days_left
FROM rentals
WHERE id = 14;

UPDATE rentals
SET days_left = NULL;







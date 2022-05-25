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
    rented       boolean     NOT NULL,
    days_left    int         NOT NULL,
    review_count int         NOT NULL,
    review_sum   int         NOT NULL,
    review_avg   float       NOT NULL,
    esrb         VARCHAR(5)  NOT NULL,
    date_added   date        NOT NULL
);

INSERT INTO games
(name, producer, console, rented, days_left, review_count, review_sum, review_avg, esrb, date_added)
VALUES ('Minecraft', 'Mojang', 'PC', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE),
       ('Rooster and Gall', 'Duty Toot', 'Xbox', FALSE, 0, 0, 0, 3.0, 'E', CURRENT_DATE),
       ('Call of Duty', 'Activision', 'Xbox', FALSE, 0, 0, 0, 3.0, 'T', CURRENT_DATE),
    ('Call of Duty', 'Activision', 'PC', FALSE, 0, 0, 0, 3.0, 'T', CURRENT_DATE),
        ('Call of Duty', 'Activision', 'PlayStation', FALSE, 0, 0, 0, 3.0, 'T', CURRENT_DATE),
        ('DOOM', 'id Software','PC', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Apex Legends', 'Respawn Entertainment','PC', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Apex Legends', 'Respawn Entertainment','Xbox', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Apex Legends', 'Respawn Entertainment','PlayStation', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE),
       ('Valheim','Iron Gate AB', 'PC', FALSE, 0, 0, 0, 3.0, 'M', CURRENT_DATE);

CREATE DOMAIN email AS varchar(40) CHECK (VALUE LIKE '%@%');
CREATE DOMAIN passwordDom AS varchar(30); /* Here in case we want to change the rules */
CREATE DOMAIN bDay AS date CHECK (EXTRACT(YEAR FROM AGE(VALUE)) >= 13);

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
    balance          int,
    age              int
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
 DROP TABLE IF EXISTS transactions CASCADE ;
CREATE TABLE transactions
(
    id       SERIAL PRIMARY KEY,
    username varchar(30) NOT NULL,
    action     varchar(50) NOT NULL,
    date     date,

    FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
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
    active                boolean     NOT NULL,
    overdue               boolean /* TODO extract from daysLeft */,

    FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE,
    FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
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

INSERT INTO transactions
    (username, action, date)
VALUES ('bob','fucking died', current_date);

SELECT *
FROM transactions;

SELECT *
FROM transactions
ORDER BY id DESC
LIMIT 1;

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
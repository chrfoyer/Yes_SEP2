DROP SCHEMA game_test CASCADE;

CREATE SCHEMA IF NOT EXISTS game_test;

SET SCHEMA 'game_test';

CREATE TABLE IF NOT EXISTS games
(
    id         SERIAL PRIMARY KEY,
    name       varchar(60),
    producer   varchar(40),
    console    varchar(15),
    rented     boolean, /* TODO obtain boolean with trigger?*/
    avg_review float, /* TODO obtain through trigger aggregating game */
    esrb       varchar(5),
    date_added date
);

CREATE DOMAIN email AS varchar(40) CHECK (VALUE LIKE '%@%');
CREATE DOMAIN passwordDom AS varchar(30); /* Here in case we want to change the rules */
CREATE DOMAIN bDay AS date CHECK (13 >= DATE_PART('year', CURRENT_DATE) - DATE_PART('year', value));

CREATE TABLE IF NOT EXISTS users
(
    username         varchar(30) PRIMARY KEY ,
    password         passwordDom NOT NULL,
    is_admin         boolean,
    email            email       NOT NULL,
    address          varchar(100),
    name             varchar(30),
    bDay             bDay        NOT NULL,
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
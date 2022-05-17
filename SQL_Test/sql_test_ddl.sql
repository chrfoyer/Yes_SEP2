CREATE SCHEMA IF NOT EXISTS gameTest;

SET SCHEMA 'gameTest';

CREATE TABLE IF NOT EXISTS gameTest.author(
    id SERIAL PRIMARY KEY,
    name varchar(40)
);

DROP TABLE gameTest.games;

CREATE TABLE IF NOT EXISTS gameTest.games(
    id SERIAL PRIMARY KEY,
    name varchar(60),
    producer varchar(40),
    console varchar(15),
    rented boolean,
    daysLeft int,
    review float,
    esrb varchar(5),
    dateAdded date
)
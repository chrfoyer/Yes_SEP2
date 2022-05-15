CREATE SCHEMA gameTest;

SET SCHEMA 'gameTest';

CREATE TABLE gameTest.author(
    id SERIAL PRIMARY KEY,
    name varchar(40)
);

CREATE TABLE gameTest.games(
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
### Wild_tracker
This is a wildlife_tracker project that allows you to see a different animals ,the names of the rangers,the sightings and whether they are endangered or not.

## Description

*This application is built using Java and it allows a user to add a new ranger,sighting,animal and what characterizes that animal.

## Setup/Installation Requirements

-Install Java and gradle.
- DATABASE :
    *psql (make sure that you have installed Postgres SQL).
    *CREATE DATABASE wildlife_tracker.
    *CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker.
    *CREATE TABLE animals (id serial PRIMARY KEY, name varchar, health varchar, age varchar, endangered boolean).
    *CREATE TABLE sightings (id serial PRIMARY KEY, location varchar, rangerName varchar).

## Technologies Used

* Java.
* Spark.
* Gradle.

## Support and Contact Details

E-mail: blandineuwase222@gmail.com .

## License

Copyright(c)2019 @BlandineK.

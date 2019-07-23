# Spotify Store API 
[![CircleCI](https://circleci.com/gh/andreassisbaroni/spotify.store.api/tree/master.svg?style=shield)](https://circleci.com/gh/andreassisbaroni/spotify.store.api/tree/master)

Spring boot application to buy music albums.

All albums are collect through a integration with Spotify API,  are available only four genres 'Rock', 'POP', 'Classic' and 'MPB'.

## Clone

To get source code, run: `git clone https://github.com/andreassisbaroni/spotify.store.api.git`.

## Build
To build project, run `mvn clean install` with this command you will execute all tests, if you want to skip tests, run `mvn clean install -DskipTests`.
If you just want to run tests, you can run `mvn test`.

## Run
You will need a local PostgreSql instance on port 5432 with a database named `spotifystore`, currently username is `postgres` and password is `123123`, you need to change this if necessary.

After making sure the database is correctly, open a terminal into root folder and run command `java -jar target/spotifyStore.jar`.
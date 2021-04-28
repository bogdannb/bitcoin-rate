# bitcoin-rate

This is a repository that I used for an exercise.
It contains a working setup with the following frameworks:
- Spring 5;
- Spring Boot 2.4.5;
- Spring Boot Cassandra for connecting to a local Apache Cassandra database;
- Spring WebFlux;
- Swagger;
- Lombok;
- Reactor tests (Unit Tests + Integration Tests)

The application built here loads the Bitcoin to USD rate from time to time (configurable) from Coindesk site and stores it in Cassandra.
Some endpoints are then exposed for getting the Bitcoin to USD latest rate & historical rates.

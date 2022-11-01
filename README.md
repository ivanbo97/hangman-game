# Hangman Game 

 The purpose of this app is to demonstrate the evolution of technologies in Java Enterprise world. It can be used as a roadmap for anyone who has already learned Java fundamentals and want to dive into enterprise development. Everything is put around developing hangman game application with statistics and rankings for players.
 

## Topics covered

Every new topic of the learning path can be found on separate branch of this repository. Bellow is the current list of topics and their corresponding branches.
* **Servlet API, JSP, Spring Core, JUnit5, AssertJ, Mockito -> dev branch**

* **Spring MVC, Selenium, MockMVC -> selenium-integration branch**

* **Spring Boot, Docker basics, Dockerfile -> spring-boot-intro branch**

* **Spring Data JPA, Hibernate, Specifications, Criteria API, Flyway(DB migrations), Docker Compose-> jpa-intro branch**

* **Web Services - SOAP (xml, wsdl files, messages, JAX-WS), REST (json, PayPal standards, TolerantReader pattern, HATEOAS, OpenAPI), List of tools used : ApacheCXF, Jackson, Swagger-UI, WebClient(for testing), RestAssured(for testing) -> web-services branch**

* **JavaScript, ReactJS, react hooks (useEffect,useState, custom), create-react-app, SWR, react-bootstrap,react-router, react-intl-18n-> reactjs-intro branch**

* **Apache Shiro Security-> security-intro branch**

## Getting started

* Requirements: Java 14, Maven, Docker 

1. Run mvn install
2. Pull the docker image: **docker pull ivanbo97/hangman-game:latest
3. In the root folder of the project (where docker-compose.yml is located) run: **docker compose up
4. Now you should have locally running two services: MySQL and Hangman game. You can acess the app on localhost:8080 and there you go :)


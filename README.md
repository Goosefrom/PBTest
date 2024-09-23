# Test for PB

##Description
Web service with api and small ui to find information about bank by card number.

##Technologies
- Java 17
- Spring Boot 3.3.3
- PostgreSQL
- Maven
- Docker

##Start Guide
You need to have docker.
- Download the project;
- Go into project directory and open cmd or IDE;
- Enter the commands or do with IDE configurations:
  - ```mvn install -DskipTests```
  - ```docker build -t pb-test.jar .```
  - ```docker-compose up -d```
- Enjoy your life.

##Endpoints
- API (could do in Postman): 
  - POST request: http://localhost:8080/api/ with body pattern: {"card":"16 numbers"}
- UI: 
  - http://localhost:8080/
## Introduction
This Rabbit Holes project is a web application that contains quite a few security holes. It attempts to demonstrate how a Java web application could be easily vulnerable if secure coding practices are ignored.

This application uses

- Spring Boot 1.5.2.RELEASE
- Spring Web MVC and REST-ful
- Thymeleaf 3
- Spring Data JPA / Hibernate
- Flyway
- JUnit
- Project Lombok (development purpose, reducing Java boilerplate code)

## Prerequisites
This application has the following software prerequisites:

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)
* [Project Lombok](https://projectlombok.org/download.html) (select correct plugin for your IDE)

## Running The Application

### Preparations

* Install a database for the application if you don't want to use default embedded H2 database.

### Configuration

* Update the database connection in the file `resources/application.yml`
```
url: jdbc:h2:~/rabbitholes_db
username: sa
password:
driver-class-name: org.h2.Driver
```

* Correct the DB schema in the file `resourses/schema.sql` if it doesn't work with your database

* Configure the location of the upload files in the file `resources/application.yml`
```
rabbitholes:
    upload-location: ./upload
```

### Building
```
mvn clean package
```

### Deployment
You can run the application in development mode:
```
mvn spring-boot:run
```

Or you can build and run as a packaged application:
```
java -jar target/rabbitholes-0.0.1-SNAPSHOT.jar
```
###  Explanation of the vulnerability
SQL Injection
```
Search query was created by String builder append. 
It will make SQL Inject when user query by SQL command.
For example : ='OR'1=1
```
Clear text Storage of Sensitive Information in a Cookie 
```
Saving email of user in cookie without hash.
```
CSRF
```
Using GET for method change attribute.
Not using token for form submition.
```
Weak password 
```
Using user password without encrypt.
Using default DB username, password
```
XSS
```
Not check Script or HTML type in Comment input of Upload File page.
```
Dangerous file upload
```
Not check file extension. file .php can be excute while clicked
```

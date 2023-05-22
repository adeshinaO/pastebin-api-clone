### Overview

- API for a pastebin-like service. It allows users to create texts that can be shared with others using a short link. 
The size of text allowed is configurable. The service returns a short unique reference which can be used to retrieve 
the text. 

- No identity or access management is implemented. It is assumed that the service runs in a private cloud only 
  accessible through devices on the company's network.

- The service runs a job that deletes texts that are set to expire at a certain date. Both the schedule of the job 
  and the max number of items deleted in one run are configurable.

### Quick API Documentation

1. `POST http://localhost:8081`

Request Body:

```json
{
	"content": "I have some text to share. YES!",
	"hasExpiryDate": true,
	"expiryDate": "2023-12-12T01:00:00+01:00"
}
```

Response Body(201):

```json
{
	"content": "I have some text to share. YES!",
	"expiryDate": "2023-12-12T01:00:00+01:00",
	"hasExpiryDate": true,
	"reference": "f3f0ccb"
}
```

2. `GET http://localhost/{reference}`

Response(200)

```json
{
  "content": "I have some text to share. YES!",
  "expiryDate": "2023-12-12T01:00:00+01:00",
  "hasExpiryDate": true,
  "reference": "f3f0ccb"
}
```

### Run Locally Using The Maven Wrapper

Easiest Way to start the application is to run the following command at the root of the project:

```shell
  ./mvnw spring-boot:run
```

It compiles, tests and runs the service. Exposing it on port 8081. A user interface for the embedded H2 database is 
also available at `http://localhost:8080/h2-console/`

### Possible Improvements

- Use a library like `shedlock` to ensure only one instance is executing scheduled jobs at any given time.
- Use Flyway to manage database setup and Migration.
- Write Integration tests for all endpoints.
- Document service layer methods.
- Version and document APIs.
- Store texts in block storage service like Amazon EBS.
- Use a dedicated key generation service to generate unique references.
- Implement IAM to give users more control over the texts they share.
- Use Docker to containerize the application.

### Main Libraries and dependencies

- Java 17
- Spring Boot (Data JPA, Web MVC, Core)
- H2 (Embedded SQL Database)
- Docker

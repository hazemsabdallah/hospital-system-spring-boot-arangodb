# hospital-system-spring-boot-arangodb
Simple hospital system based around two entities for hospitals and patients. Featuring basic CRUD support and graph relations to register patients into hospitals.

A Swagger integration is available at `http://localhost:8080/swagger-ui/index.html` for better visibility over the exposed endpoints.

**Note:** before running the project, you first need to modify the `application.properties` to configure the DB connection. There is no need to create the database or collections manually as Spring Data will take care of this step.

#### Tech Stack

- Java Spring Boot
- Maven Build Tool
- ArangoDB

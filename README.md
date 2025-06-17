# 📘 Spring Boot JDBC + JPA + PostgreSQL Demo

This project is a hands-on implementation of a RESTful API using Spring Boot, JDBC, JPA, and PostgreSQL. It's inspired by the tutorial: [The ULTIMATE Guide to Spring Boot: Spring Boot for Beginners](https://www.youtube.com/watch?v=Nv2DERaMx-4) by Devtiro.

## 🛠️ Technologies Used

- **Spring Boot**: Simplifies the development of stand-alone, production-grade Spring applications.
- **Spring JDBC**: Provides a JDBC-abstraction layer for easier database interactions.
- **Spring Data JPA**: Simplifies data access using JPA and Hibernate.
- **PostgreSQL**: An advanced open-source relational database.
- **RESTful API**: Implements standard HTTP methods for CRUD operations.
- **Controllers**: Handle incoming HTTP requests and map them to services.
- **Integration Testing**: Ensures that different parts of the application work together as expected.
- **Maven**: For project and dependency management.
- **Docker Compose**: Used to spin up a PostgreSQL container for local development.

## 📁 Project Structure
```
database_jdbc/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── controller/        # REST Controllers
│   │   │           ├── entity/            # JPA Entities
│   │   │           ├── repository/        # Spring Data JPA Repositories
│   │   │           └── service/           # Business Logic Layer
│   │   └── resources/
│   │       ├── application.properties     # App configuration including DB connection
│   │       └── …
├── docker-compose.yml                     # PostgreSQL container config
├── pom.xml                                # Maven dependencies and build config
└── README.md                              # Project documentation
```
## ⚙️ Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Docker (for PostgreSQL)

### Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/kris175/database_jdbc.git
cd database_jdbc
```
2. **Start PostgreSQL with Docker**
```bash
docker-compose up -d
```
3. **Update application.properties**
```
spring.application.name=database_jdbc
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
```
4. **Build and run the application**
```bash
mvn spring-boot:run
```

## 📬 API Endpoints

Below are the example REST API endpoints implemented in the project:

### Authors API

| Method | Endpoint              | Description               |
|--------|-----------------------|---------------------------|
| GET    | `/authors/`           | Retrieve all authors      |
| GET    | `/authors/{id}`       | Retrieve author by id     |
| POST   | `/authors/{id}`       | Create a new author       |
| PUT    | `/authors/{id}`       | Update an existing author |
| PATCH  | `/authors/{id}`       | Partially update an existing author |
| DELETE | `/authors/{id}`       | Delete a record by author |

### Books API

| Method | Endpoint              | Description             |
|--------|-----------------------|-------------------------|
| GET    | `/books/`             | Retrieve all books      |
| GET    | `/books/{isbn}`       | Retrieve record by ISBN |
| POST   | `/books/{isbn}`       | Create a new book       |
| PUT    | `/books/{isbn}`       | Update an existing book |
| PATCH    | `/books/{isbn}`     | Partially update an existing book |
| DELETE | `/books/{isbn}`       | Delete a record by ISBN |

## 📝 Acknowledgments
Tutorial by [Devtiro](https://www.youtube.com/@devtiro)

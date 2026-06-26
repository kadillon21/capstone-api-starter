# Capstone E-Commerce API

A RESTful e-commerce backend built with **Spring Boot 4**, **Java 17**, **MySQL**, and **JWT authentication**. It powers an online store: browsing and searching products, managing a shopping cart, editing a user profile, and checking out to create orders. Admin users can manage products and categories.

This is the Year Up capstone API. It ships with five interchangeable seed databases (video game store, record shop, grocery store, clothing store, and the classic EasyShop) so the same codebase can run against different storefronts.

---

## Tech Stack

| Layer | Choice |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.0.2 |
| Security | Spring Security + JWT (jjwt 0.11.1) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL 8 (H2 in-memory for tests) |
| Docs | springdoc-openapi + Swagger UI |
| Build | Maven (wrapper included) |

---

## Architecture

The project follows a standard layered Spring Boot structure:

```
Controller  →  Service  →  Repository  →  MySQL
   (HTTP)      (logic)     (Spring Data JPA)
```

- **Controllers** (`org.yearup.controllers`) handle HTTP, validation, and status codes.
- **Services** (`org.yearup.service`) hold business logic (checkout, cart math, search filtering).
- **Repositories** (`org.yearup.repository`) are Spring Data JPA interfaces over the database.
- **Models** (`org.yearup.models`) are JPA entities; **DTOs** (`org.yearup.models.dto`) shape request/response payloads.
- **Security** (`org.yearup.security`) wires up the JWT filter, token provider, and access handlers.

---

## Prerequisites

- JDK 17+
- MySQL 8 running locally on port 3306
- (Maven is bundled via the `mvnw` wrapper — no separate install needed)

---

## Setup

### 1. Create a database

Pick one of the seed scripts in `database/` and run it in MySQL. For example, the video game store:

```bash
mysql -u root -p < database/create_database_videogamestore.sql
```

Each script drops and recreates its database, then loads users, profiles, categories, products, and sample cart items.

### 2. Configure credentials

The app reads database credentials and the active store name from environment variables (with defaults baked into `application.properties`):

| Variable | Default | Purpose |
|---|---|---|
| `DB_NAME` | `videogamestore` | Which database to connect to (also picks the startup banner) |
| `DB_USERNAME` | `root` | MySQL username |
| `DB_PASSWORD` | `password` | MySQL password |

### 3. Run

```bash
DB_NAME=videogamestore DB_USERNAME=root DB_PASSWORD='your_password' ./mvnw spring-boot:run
```

> Wrap passwords with special characters in **single quotes** so the shell doesn't interpret them.

The API starts on **http://localhost:8080**.

---

## API Documentation

Once running, interactive Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

The full OpenAPI spec lives in `openapi.yaml`.

---

## Authentication

All protected endpoints expect a JWT bearer token.

1. **Register** (optional — seed users already exist):
   ```http
   POST /register
   { "username": "johndoe", "password": "p@ssw0rd", "confirmPassword": "p@ssw0rd", "role": "ROLE_USER" }
   ```
2. **Login** to get a token:
   ```http
   POST /login
   { "username": "user", "password": "password" }
   ```
3. Pass the token on every protected request:
   ```
   Authorization: Bearer <token>
   ```

**Seed accounts** (password is `password` for all):

| Username | Role |
|---|---|
| `user` | ROLE_USER |
| `admin` | ROLE_ADMIN |
| `george` | ROLE_USER |

Tokens expire after 30 hours.

---

## Endpoints

| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/login` | Public | Authenticate, get JWT |
| POST | `/register` | Public | Create an account |
| GET | `/products` | Public | Search/list products (filters: `cat`, `minPrice`, `maxPrice`, `subCategory`) |
| GET | `/products/{id}` | Public | Get one product |
| POST | `/products` | Admin | Add a product |
| PUT | `/products/{id}` | Admin | Update a product |
| DELETE | `/products/{id}` | Admin | Delete a product |
| GET | `/categories` | Public | List categories |
| GET | `/categories/{id}` | Public | Get one category |
| GET | `/categories/{id}/products` | Public | Products in a category |
| POST/PUT/DELETE | `/categories/...` | Admin | Manage categories |
| GET | `/cart` | User | View cart |
| POST | `/cart/products/{productId}` | User | Add to cart |
| PUT | `/cart/products/{productId}` | User | Update item quantity |
| DELETE | `/cart` | User | Clear cart |
| GET | `/profile` | User | View profile |
| PUT | `/profile` | User | Update profile |
| POST | `/orders` | User | Checkout — turn cart into an order |

---

## Testing

```bash
./mvnw test
```

Repository tests run against an in-memory **H2** database seeded from `src/test/resources/test-insert-data.sql`, so they don't touch your real MySQL data.

---

## Project Structure

```
src/main/java/org/yearup/
├── ECommerceApplication.java     # entry point + banner selection
├── controllers/                  # REST endpoints
├── service/                      # business logic
├── repository/                   # Spring Data JPA interfaces
├── models/                       # JPA entities + DTOs
└── security/                     # JWT filter, token provider, config
database/                         # seed SQL for 5 storefronts
openapi.yaml                      # API specification
```

---

## License

Year Up United capstone project.

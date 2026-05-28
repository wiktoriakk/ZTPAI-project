# ShopManager

Full-stack web application built with Spring Boot (REST API) and Angular.

## Tech Stack

**Backend**
- Java 17, Spring Boot 4
- Spring Security + JWT
- Spring Data JPA + H2 (in-memory)
- JUnit 5 + Mockito

**Frontend**
- Angular 21 (standalone components, SSR)
- TypeScript

---

## Prerequisites

- Java 17+
- Node.js 20+ / npm 11+
- Maven (or use included `mvnw`)

---

## Running the Application

### 1. Backend

```bash
cd backend
./mvnw spring-boot:run        # Linux/Mac
.\mvnw.cmd spring-boot:run    # Windows
```

Backend starts on **http://localhost:8080**

On startup, two test accounts are created automatically:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |

H2 Console (dev only): http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:mem:testdb`

### 2. Frontend

```bash
cd frontend
npm install
npm start
```

Frontend starts on **http://localhost:4200**

---

## API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register new user (role: USER) |
| POST | `/api/auth/login` | Public | Login, returns JWT token |

### Products
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/products` | USER, ADMIN | Get all products |
| GET | `/api/products/{id}` | USER, ADMIN | Get product by ID |
| POST | `/api/products` | ADMIN only | Create product |
| DELETE | `/api/products/{id}` | ADMIN only | Delete product |

**Authentication:** pass JWT token in header:
```
Authorization: Bearer <token>
```

---

## Running Tests

```bash
cd backend
./mvnw test        # Linux/Mac
.\mvnw.cmd test    # Windows
```

6 unit tests covering `ProductService`:
- happy path (get by id, create)
- negative case (entity not found)
- business validation (blank/null name)

---

## Project Structure

```
proj/
├── backend/
│   └── src/main/java/pl/edu/pk/proj/
│       ├── config/       # Security, CORS, DataInitializer
│       ├── controller/   # REST endpoints
│       ├── dto/          # Request/Response DTOs + Mapper
│       ├── event/        # Spring Application Events
│       ├── model/        # JPA Entities
│       ├── repository/   # Spring Data repositories
│       ├── security/     # JWT filter + utils
│       └── service/      # Business logic
└── frontend/
    └── src/app/
        ├── components/   # Login, ProductList
        ├── guards/       # AuthGuard
        └── services/     # AuthService, ProductService, Interceptor
```

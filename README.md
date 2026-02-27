# Article Service

Production-ready REST microservice built with Quarkus 3, PostgreSQL and Liquibase.

Provides paginated, filterable and validated article management with versioned database schema, structured error handling and integration testing.

---

## 🚀 Tech Stack

- Java 17+
- Quarkus 3.x
- Hibernate ORM with Panache
- PostgreSQL (Dev/Prod)
- H2 (Test profile)
- Liquibase (database versioning)
- RESTEasy + Jackson
- Bean Validation (Jakarta Validation)
- SmallRye OpenAPI
- SmallRye Health
- JUnit 5 + RestAssured

---

## 🏗 Architecture

The service follows a layered architecture with strict separation of concerns:

- **Resource Layer** → HTTP contract (REST endpoints)
- **DTO Layer** → API boundary models
- **Service Layer** → Business logic and orchestration
- **Entity Layer** → Persistence model (Panache ORM)
- **Database Versioning** → Liquibase changelogs

### Key Design Decisions

- Validation rules implemented at Service level
- Database constraints used as safety net
- Global `ExceptionMapper` for consistent error responses
- DTO separation (no direct entity exposure)
- Profile-based configuration: `%dev`, `%test`, `%prod`
- Pagination with explicit validation (`size > 0`, `page >= 0`)

---

## 📦 Project Structure

```text
src/main/java
└── com.editorial.platform.article
    ├── api
    │   ├── ArticleResource
    │   └── exception
    │       ├── ConstraintViolationExceptionMapper
    │       ├── ArticleNotFoundExceptionMapper
    │       └── GlobalExceptionMapper
    ├── dto
    │   ├── ArticleRequest
    │   ├── ArticleResponse
    │   ├── ErrorResponse
    │   └── PagedResponse
    ├── service
    │   └── ArticleService
    ├── domain
    │   └── Article
    └── health

src/main/resources
└── db/changelog
    ├── db.changelog-master.xml
    ├── 001-init-schema.xml
    └── 002-article-constraints.xml
```


---

## ▶️ Running the Application

### Dev Mode (with Dev Services)

```bash
./mvnw quarkus:dev
```

Quarkus Dev Services automatically starts PostgreSQL via Docker.

Application runs at:

```
http://localhost:8080
```

OpenAPI UI:

```
http://localhost:8080/q/swagger-ui
```

Health endpoint:

```
http://localhost:8080/q/health
```

---

## 🧪 Running Tests

```bash
./mvnw test
```

Test configuration:

- H2 in-memory database
- Liquibase executed on startup
- `@QuarkusTest` integration tests
- RestAssured for HTTP validation
- Validation and pagination scenarios covered

## 📡 API Endpoints

### GET /articles

Supports:

- Pagination (`page`, `size`)
- Filtering (`author`, `title`)
- Sorting (`sortBy`, `direction`)

#### Query Parameter Rules

- `page >= 0`
- `1 <= size <= 100`
- `sortBy ∈ {id, title, author}`
- `direction ∈ {asc, desc}`

---

### POST /articles

Creates a new article.

#### Request Body

```json
{
  "title": "string (not blank)",
  "author": "string (not blank)"
}
```

#### Returns

- `201 Created`
- `400 Bad Request` (validation error)
- `409 Conflict` (business rule violation, if applicable)
- `500 Internal Server Error`

---

## ⚠️ Error Contract

All errors follow a standardized structure:

```json
{
  "timestamp": "2026-02-27T18:32:21Z",
  "status": 400,
  "error": "Bad Request",
  "message": "title must not be blank",
  "path": "/articles"
}
```

Handled via global `ExceptionMapper` implementations.

---

## 🛠 Database Versioning

Liquibase manages schema evolution.

### Changelogs

```text
db/changelog/
 ├── db.changelog-master.xml
 ├── 001-init-schema.xml
 └── 002-article-constraints.xml
```

Includes:

- Table creation
- Sequence generation
- NOT NULL constraints
- Indexes

Liquibase runs automatically in all profiles.

---

## ❤️ Health Checks

Custom readiness checks implemented.

Available at:

```
/q/health
```

---

## 📈 Project Roadmap

The evolution of this project follows a structured four-phase plan.

---

### 🔹 Phase 1 — Professionalize the Service

Goal: Transform a basic CRUD service into a production-ready microservice.

- Environment profiles: `%dev`, `%test`, `%prod`
- Database versioning with Liquibase
- Health checks (liveness, readiness, custom database check)
- Comprehensive integration tests
- Professional technical README in English

Status: ✅ Completed

---

### 🔹 Phase 2 — Introduce Kafka (Event-Driven Architecture)

Goal: Introduce asynchronous communication and real event-driven design.

- Publish `ArticleCreatedEvent` to Kafka
- Implement a new consumer microservice (analytics or notification)
- Enable asynchronous inter-service communication
- Establish a real event-driven architecture

---

### 🔹 Phase 3 — Separate into Independent Microservices

Goal: Move from logical separation to physical service independence.

- `article-service`
- `analytics-service`
- Communication exclusively via Kafka
- Independent deployment capability

---

### 🔹 Phase 4 — Kubernetes Deployment

Goal: Prepare the system for container orchestration.

- `deployment.yaml`
- `service.yaml`
- `ConfigMap`
- `Secret`
- Local deployment documentation (kind or minikube)

---

## 📄 License

This project is intended for educational and portfolio purposes.
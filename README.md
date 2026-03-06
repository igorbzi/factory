# 🏭 Factory Optimizer

System for managing industrial inputs and optimizing production to maximize revenue.

The application allows registering raw materials and products (with their compositions), and uses **Linear Programming (LP)** to suggest the optimal production plan based on current stock — guaranteeing the globally best solution.

---

## 🧱 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17 + Spring Boot 3 |
| Frontend | Vue 3 + TypeScript + PrimeVue |
| Database | PostgreSQL |
| State Management | Pinia |
| Optimization | ojAlgo (LP Solver) |

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+
- Docker (optional)

---

## 🗄️ Database Setup

### Option 1 — Docker (recommended)

```bash
docker run --name factory-db \
  -e POSTGRES_DB=factory \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres
```

To stop and remove the container:

```bash
docker stop factory-db
docker rm factory-db
```

### Option 2 — Local PostgreSQL

1. Install PostgreSQL from https://www.postgresql.org/download
2. Create the database:

```sql
CREATE DATABASE factory;
```

3. Make sure the user `postgres` with password `postgres` exists, or update `application.properties` with your credentials.

---

## 🚀 Running the Backend

```bash
cd backend
mvn spring-boot:run
```

API will be available at: `http://localhost:8080`

### Database configuration

In `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/factory
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
```

---

## 🖥️ Running the Frontend

```bash
cd frontend
npm install
npm run dev
```

App will be available at: `http://localhost:5173`

---

## 📡 API Endpoints

### Raw Materials
| Method | Endpoint | Description |
|---|---|---|
| GET | `/raw-materials` | List all |
| GET | `/raw-materials/{id}` | Get by id |
| POST | `/raw-materials` | Create |
| PUT | `/raw-materials/{id}` | Update |
| DELETE | `/raw-materials/{id}` | Delete |

### Products
| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | List all |
| GET | `/products/{id}` | Get by id |
| POST | `/products` | Create |
| PUT | `/products/{id}` | Update |
| DELETE | `/products/{id}` | Delete |

### Production Optimizer
| Method | Endpoint | Description |
|---|---|---|
| GET | `/production/optimize` | Run optimization |

---

## 🧪 Running Tests

```bash
cd backend
mvn test
```

---

## 📁 Project Structure

```
factory-optimizer/
├── backend/
│   └── src/
│       ├── main/java/com/factory/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── exception/
│       │   ├── repository/
│       │   └── service/
│       └── test/
└── frontend/
    └── src/
        ├── components/
        ├── router/
        ├── services/
        ├── stores/
        ├── types/
        └── views/
```
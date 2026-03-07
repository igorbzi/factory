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


### Option 1 — Terminal

```bash
cd factory-api
mvn spring-boot:run
```

### Option 2 — IntelliJ IDEA

1. Open IntelliJ and select **File → Open** and choose the `factory-api` folder
2. Wait for Maven to download the dependencies
3. Locate the main class `FactoryApplication.java` in `src/main/java/com/projedata/factory`
4. Click the ▶ green button next to the class declaration, or press `Shift + F10`

### Option 3 — Eclipse / Spring Tools Suite (STS)

1. Select **File → Import → Existing Maven Projects**
2. Select the `factory-api` folder and click **Finish**
3. Wait for Maven to download the dependencies
4. Right-click `FactoryApplication.java` → **Run As → Spring Boot App**

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
cd factory-frontend
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

### Option 1 — Terminal
```bash
cd factory-api
mvn test
```

### Option 2 — IntelliJ IDEA

1. Right-click the `test` folder in `src/test/java`
2. Select **Run 'All Tests'**

Or run a specific test class:
1. Open `ProductionOptimizerServiceTest.java`
2. Click the ▶ green button next to the class declaration, or press `Shift + F10`

### Option 3 — Eclipse / Spring Tools Suite (STS)

1. Right-click the `src/test/java` folder
2. Select **Run As → JUnit Test**

Or run a specific test class:
1. Right-click `ProductionOptimizerServiceTest.java`
2. Select **Run As → JUnit Test**
---

## 📁 Project Structure

```
projedata/
├── factory-api/
│   └── src/
│       ├── main/java/com/projedata/factory/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── enumerators/
│       │   ├── exception/
│       │   ├── repository/
│       │   └── service/
│       └── test/
└── factory-frontend/
    └── src/
        ├── components/
        ├── router/
        ├── services/
        ├── stores/
        ├── types/
        └── views/
```

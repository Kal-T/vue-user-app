# User Management Monorepo (Vue 3 + Spring Boot 3)

Welcome to the **User Management System** monorepo. This repository houses a fully decoupled, production-grade application featuring a high-aesthetic Vue 3 single-page frontend (built with Vite, TypeScript, and Tailwind CSS) and a robust Spring Boot 3 REST API backend (built with Java 21 and H2 database).

The project is architected around two core patterns:
- **Frontend**: Strict *Stateful Parent / Stateless Child* component isolation.
- **Backend**: Standard *Controller-Service-Repository* transactional design with unified CORS and rest-validation mapping rules.

---

## 📂 Monorepo Folder Structure

```
vue-user-app/
├── .gitignore              # Monorepo root exclusions (Node, Java, Maven, IDEs)
├── README.md               # Master monorepo guide (this file)
├── frontend/               # Vue 3 Single Page Application (Vite + TS + Tailwind)
│   ├── package.json        # Node dependency configurations
│   ├── tailwind.config.js  # Tailwind scan properties
│   ├── .env.development    # Local environment parameters
│   └── src/                # Front-end Vue 3 source
│       ├── api/            # Axios client layers
│       ├── composables/    # Atomic composition state managers
│       ├── types/          # Contract interfaces
│       └── components/     # Stateless UI presentation nodes
└── backend/                # Spring Boot REST API Service (Java 21 + H2)
    ├── pom.xml             # Maven dependencies configuration
    ├── mvnw                # Maven wrapper shell script
    └── src/main/           # Java API source
        ├── resources/      # Application properties configurations
        └── java/           # Java packages (controllers, services, entities)
```

---

## 🛠️ System Prerequisites

Ensure you have the following frameworks installed before booting the stack:
- **Node.js**: `v18.0.0` or higher
- **Java JDK**: `v21` or higher
- **Maven**: Optional (bundled via Maven Wrapper `mvnw` locally)

---

## 🚀 Running the Stack

To launch both systems simultaneously, open two terminal windows:

### 1. Running the Backend Service
Navigate to the `backend` directory, authorize execution for the Maven wrapper, and start the Spring Boot server:
```bash
cd backend
chmod +x mvnw
./mvnw spring-boot:run
```
- **API Server Address**: `http://localhost:8081`
- **H2 In-Memory Console**: `http://localhost:8081/h2-console`
  - *JDBC URL*: `jdbc:h2:mem:userappdb`
  - *Username*: `sa` (password: leave blank)

### 2. Running the Frontend client
Navigate to the `frontend` directory, install node dependencies, and boot up Vite's dev server:
```bash
cd frontend
npm install
npm run dev
```
- **Vite Hot-Reload Server Address**: `http://localhost:5173`

---

## 🔌 API Endpoints Table

The backend REST service exposes **7 operational endpoints**:

| Method | Path | Description | Response Codes |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/health` | Check backend system health status | `200 OK` |
| **GET** | `/api/users` | Retrieve all users or filter users by optional classification `?role=A\|U` | `200 OK` |
| **GET** | `/api/users/summary` | Fetch computed summary counts (total counts, admin counts, user counts) | `200 OK` |
| **GET** | `/api/users/{id}` | Fetch a single user record by unique identifier ID | `200 OK`, `404 Not Found` |
| **POST** | `/api/users` | Create and register a new user in the system (performs validations) | `201 Created`, `400 Bad Request`, `409 Conflict` |
| **PUT** | `/api/users/{id}` | Update and modify fields of an existing user record | `200 OK`, `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| **DELETE** | `/api/users/{id}` | Delete and terminate an existing user account from the registry | `204 No Content`, `404 Not Found` |

---

## ⚙️ Environment Variables Table

Configurations are externalized and manageable via properties:

| Variable | Platform | File | Default Value | Description |
| :--- | :--- | :--- | :--- | :--- |
| `VITE_API_URL` | Frontend | `.env.development` | `http://localhost:8081` | Specifies the base HTTP target path where Axios sends client API requests. |
| `app.cors.allowed-origin` | Backend | `application-dev.yml` | `http://localhost:5173` | Defines origin whitelist patterns to permit cross-origin access and CORS pre-flights. |

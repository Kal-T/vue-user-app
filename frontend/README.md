# User Management SPA (Vue 3 + Vite + TypeScript + Tailwind CSS)

A modern, high-performance User Management dashboard designed around clean architectural patterns, focusing on **Stateful Parent vs. Stateless Child Component isolation** in Vue 3.

---

## 🚀 Technology Stack
- **Core Framework**: [Vue 3](https://vuejs.org/) (Composition API with `<script setup>` syntax)
- **Tooling & Bundler**: [Vite](https://vitejs.dev/) & [TypeScript](https://www.typescriptlang.org/)
- **Styling Engine**: [Tailwind CSS v3](https://tailwindcss.com/) & [PostCSS](https://postcss.org/)
- **State Architecture**: Pure Reactive Props-Streaming (No Pinia, No Vue Router)

---

## 🏛 Architecture & Design Patterns

This project intentionally follows a strict **Stateful Parent / Stateless Child** design pattern:

### 1. Stateful Parent (`UserManagementPage.vue`)
- **Single Source of Truth**: Owns all application-level states (`users`, `selectedUser`, `filter`, `showDetailModal`, `detailUser`).
- **Data Orchestrator**: Responsible for processing computations, executing environment actions, and handling callbacks.
- **Top Comment**:
  > `"STATEFUL PARENT — owns all application state. All child components are stateless — they receive data via props and communicate back via emits only."`

### 2. Stateless Children (`src/components/*`)
- **Presentation-Only**: Do not contain internal `ref()`, `reactive()`, or local domain state.
- **Unidirectional Data Flow**: Accept data strictly via standard compiler macro `defineProps` and notify the parent of interactions using Vue's custom compiler macro `defineEmits`.
- **Fast, Reusable, and Testable**: Decoupled from state management structures, making them extremely lightweight.

---

## 📂 Project Structure

```
frontend/
├── .env.development       # Dev environment URL (http://localhost:8081)
├── .env.production        # Prod environment URL (https://api.yourdomain.com)
├── postcss.config.js      # PostCSS plugins config
├── tailwind.config.js     # Tailwind CSS component directories matching
├── tsconfig.json          # Main TS config referencing App/Node modules
├── vite.config.ts         # Vite bundler options & alias configuration
└── src/
    ├── main.ts            # App bootsrap & Tailwind imports loader
    ├── style.css          # Core Tailwind directives entry
    ├── App.vue            # Shell layout and base wrapper
    ├── types/
    │   └── user.ts        # Common typed User contract interface
    ├── pages/
    │   └── UserManagementPage.vue # Stateful Page Parent
    └── components/        # Stateless UI components
        ├── CountComponent.vue        # Pure stat badge card
        ├── UserSummaryComponent.vue  # Reactive metric processor
        ├── UserFormComponent.vue     # Glassmorphic Form view
        ├── UserListComponent.vue     # User data-table view
        └── BaseModal.vue             # Modal dialog container overlay
```

---

## 🛠️ Scripts & Commands

Navigate to the `frontend` folder to operate the application:

```bash
cd frontend
```

### Install Dependencies
```bash
npm install
```

### Run Local Development Server
```bash
npm run dev
```
Starts Vite's hot-rebuilding server. Access the dashboard instantly via the terminal link (typically `http://localhost:5173`).

### Run Production Build & Typecheck
```bash
npm run build
```
Validates TypeScript compilation through `vue-tsc` and compiles optimized production assets.

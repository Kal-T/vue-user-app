<script setup lang="ts">
import UserManagementPage from './pages/UserManagementPage.vue'
</script>

<template>
  <main class="min-h-screen bg-slate-50 text-slate-800 py-10 px-4 sm:px-6 lg:px-8">
    <div class="max-w-7xl mx-auto space-y-8">
      <!-- Page Title at top -->
      <header class="border-b border-slate-200/80 pb-6 flex items-center justify-between">
        <div>
          <h1 class="text-4xl font-extrabold tracking-tight bg-gradient-to-r from-violet-700 via-indigo-700 to-cyan-700 bg-clip-text text-transparent">
            User Management
          </h1>
          <p class="text-xs text-slate-500 mt-2 font-medium">
            Vue 3 + Vite + TypeScript stateless component design patterns
          </p>
        </div>
        <div class="hidden sm:block">
          <span class="px-3 py-1 text-xs font-semibold rounded-full bg-violet-50 border border-violet-100 text-violet-600 shadow-sm shadow-violet-100/50">
            SPA Sandbox
          </span>
        </div>
      </header>

      <!-- Stateful Parent component containing full grid layout and data control -->
      <UserManagementPage />
    </div>
  </main>
</template>

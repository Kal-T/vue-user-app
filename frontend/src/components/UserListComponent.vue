<!-- STATELESS — filter logic is computed from props. All mutations communicated via emits. -->

<script setup lang="ts">
import { computed } from 'vue'
import type { User } from '../types/user'

const props = defineProps<{
  users: User[]
  filter: 'all' | 'admin' | 'user'
}>()

const emit = defineEmits<{
  create: []
  edit: [user: User]
  delete: [id: string]
  detail: [user: User]
  filterChange: [filter: 'all' | 'admin' | 'user']
}>()

// Computed filtered list based strictly on props
const filteredUsers = computed(() => {
  if (props.filter === 'admin') {
    return props.users.filter((u) => u.role === 'A')
  }
  if (props.filter === 'user') {
    return props.users.filter((u) => u.role === 'U')
  }
  return props.users
})

const handleDelete = (user: User) => {
  if (window.confirm(`Are you sure you want to delete ${user.name} ${user.lastName}?`)) {
    emit('delete', user.id)
  }
}
</script>

<template>
  <div class="bg-white border border-slate-200/80 rounded-2xl p-6 shadow-sm space-y-6">
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
      <div>
        <h2 class="text-xl font-bold text-slate-900">User Repository</h2>
        <p class="text-xs text-slate-500 mt-1">Manage and audit system access accounts</p>
      </div>

      <!-- Action Panel -->
      <div class="flex flex-wrap items-center gap-3">
        <!-- Filter Buttons -->
        <div class="flex rounded-lg bg-slate-100 p-1 border border-slate-200">
          <button
            v-for="f in ['all', 'admin', 'user'] as const"
            :key="f"
            @click="emit('filterChange', f)"
            :class="[
              'px-3 py-1 text-xs font-semibold rounded-md transition-all capitalize',
              filter === f
                ? 'bg-white text-slate-900 border border-slate-200/60 shadow-sm font-bold'
                : 'text-slate-500 hover:text-slate-700'
            ]"
          >
            {{ f === 'all' ? 'All' : f === 'admin' ? 'Admins' : 'Users' }}
          </button>
        </div>

        <!-- Create Button -->
        <button
          @click="emit('create')"
          class="px-4 py-2 text-xs font-semibold text-white bg-gradient-to-r from-violet-600 to-indigo-600 hover:from-violet-500 hover:to-indigo-500 rounded-lg shadow-sm border border-violet-500/20 transition-all duration-300 hover:-translate-y-0.5 active:translate-y-0"
        >
          Create New
        </button>
      </div>
    </div>

    <!-- Data Table Container -->
    <div class="overflow-x-auto border border-slate-200 rounded-xl">
      <table class="w-full text-left border-collapse">
        <thead>
          <tr class="bg-slate-50 border-b border-slate-200 text-slate-500 text-xs font-semibold uppercase tracking-wider">
            <th class="py-4 px-6">Full Name</th>
            <th class="py-4 px-6">Email Address</th>
            <th class="py-4 px-6">System Role</th>
            <th class="py-4 px-6">Location</th>
            <th class="py-4 px-6 text-right">Actions</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-slate-150 text-slate-700 text-sm">
          <tr 
            v-for="user in filteredUsers" 
            :key="user.id"
            class="hover:bg-slate-50/50 transition-colors"
          >
            <!-- Full Name -->
            <td class="py-4 px-6 font-semibold text-slate-900">
              {{ user.name }} {{ user.lastName }}
            </td>
            <!-- Email -->
            <td class="py-4 px-6 font-mono text-xs text-slate-500">
              {{ user.email }}
            </td>
            <!-- Role Badge -->
            <td class="py-4 px-6">
              <span 
                :class="[
                  'px-2.5 py-0.5 text-xs font-semibold rounded-full border',
                  user.role === 'A' 
                    ? 'bg-emerald-50 text-emerald-700 border-emerald-200/80' 
                    : 'bg-blue-50 text-blue-700 border-blue-200/80'
                ]"
              >
                {{ user.role === 'A' ? 'Admin' : 'User' }}
              </span>
            </td>
            <!-- Address -->
            <td class="py-4 px-6 text-slate-500 truncate max-w-xs" :title="user.address">
              {{ user.address }}
            </td>
            <!-- Actions -->
            <td class="py-4 px-6 text-right">
              <div class="flex items-center justify-end gap-3">
                <button
                  @click="emit('detail', user)"
                  class="text-xs font-semibold text-slate-500 hover:text-cyan-600 transition-colors"
                >
                  Detail
                </button>
                <button
                  @click="emit('edit', user)"
                  class="text-xs font-semibold text-slate-500 hover:text-violet-600 transition-colors"
                >
                  Edit
                </button>
                <button
                  @click="handleDelete(user)"
                  class="text-xs font-semibold text-red-500 hover:text-red-600 transition-colors"
                >
                  Delete
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="filteredUsers.length === 0">
            <td colspan="5" class="py-8 text-center text-slate-400 font-medium">
              No matching records found.
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

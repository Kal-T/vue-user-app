<!-- STATEFUL PARENT — owns all application state. All child components are stateless — they receive data via props and communicate back via emits only. -->

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { User } from '../types/user'
import UserFormComponent from '../components/UserFormComponent.vue'
import UserListComponent from '../components/UserListComponent.vue'
import UserSummaryComponent from '../components/UserSummaryComponent.vue'
import CountComponent from '../components/CountComponent.vue'
import BaseModal from '../components/BaseModal.vue'
import { useUsers } from '../composables/useUsers'
import { useUserForm } from '../composables/useUserForm'

// Fetch environment variable to demonstrate configurations
const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8081'

// Destructure both composables to govern all state and operations
const userActions = useUsers()
const form = useUserForm(userActions)

const { users, isLoading, error: userError, fetchUsers } = userActions
const { 
  selectedUser, 
  formError, 
  formLoading, 
  handleSave, 
  handleUpdate, 
  handleDelete, 
  selectForEdit, 
  clearSelection 
} = form

// Local purely presentation/layout UI state
const filter = ref<'all' | 'admin' | 'user'>('all')
const showDetailModal = ref<boolean>(false)
const detailUser = ref<User | null>(null)

// Trigger initial data load on mount
onMounted(() => {
  fetchUsers()
})

const onDetail = (user: User) => {
  detailUser.value = user
  showDetailModal.value = true
}

const onFilterChange = (newFilter: 'all' | 'admin' | 'user') => {
  filter.value = newFilter
}

// Temporary method to push a new user to users array to verify props reactivity
const addMockUser = () => {
  const isA = Math.random() > 0.5
  const nextNum = users.value.length + 1
  const newUser: User = {
    id: `usr-0${nextNum}`,
    name: `MockName${nextNum}`,
    lastName: isA ? 'Admin' : 'User',
    email: `mock.user${nextNum}@resistance.net`,
    role: isA ? 'A' : 'U',
    address: `${nextNum * 100} Simulation Ave, Neo City`
  }
  users.value.push(newUser)
}
</script>

<template>
  <div class="space-y-8">

    <!-- Error state banner for fetch failure -->
    <div 
      v-if="userError" 
      class="p-4 bg-red-50 border border-red-200 rounded-xl flex items-center gap-3 text-red-700 text-xs font-semibold shadow-sm"
    >
      <svg class="h-4 w-4 shrink-0 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
      </svg>
      <span>{{ userError }}</span>
    </div>

    <!-- Two-column grid: Left 40% (4 cols), Right 60% (6 cols) -->
    <div class="grid grid-cols-1 lg:grid-cols-10 gap-8">
      <!-- Left Column: UserFormComponent & CountComponent -->
      <div class="lg:col-span-4 space-y-6">
        <UserFormComponent 
          :user="selectedUser"
          :loading="formLoading"
          :error="formError"
          @save="handleSave"
          @update="handleUpdate"
          @reset="clearSelection"
        />
      </div>

      <!-- Right Column: UserSummaryComponent -->
      <div class="lg:col-span-6">
        <UserSummaryComponent :users="users" />
      </div>
    </div>

    <!-- Bottom Row: UserListComponent full width -->
    <div class="w-full">
      <UserListComponent 
        :users="users"
        :filter="filter"
        @create="clearSelection"
        @edit="selectForEdit"
        @delete="handleDelete"
        @detail="onDetail"
        @filterChange="onFilterChange"
      />
    </div>

    <!-- BaseModal (Visualizing selected user audit credentials details via slots) -->
    <BaseModal 
      :show="showDetailModal" 
      title="User Information Audit"
      @close="showDetailModal = false"
    >
      <div v-if="detailUser" class="space-y-6">
        <!-- Bio Card -->
        <div class="flex items-center gap-4 p-4 bg-slate-50 border border-slate-200 rounded-2xl">
          <div class="h-12 w-12 rounded-xl bg-gradient-to-tr from-violet-600 to-indigo-600 flex items-center justify-center font-extrabold text-white text-lg shadow-md shadow-violet-950/20">
            {{ detailUser.name[0] }}{{ detailUser.lastName[0] }}
          </div>
          <div>
            <h4 class="font-bold text-slate-900 text-base">{{ detailUser.name }} {{ detailUser.lastName }}</h4>
            <span 
              :class="[
                'inline-block px-2.5 py-0.5 text-[10px] font-semibold rounded-full border mt-1',
                detailUser.role === 'A' 
                  ? 'bg-emerald-50 text-emerald-700 border-emerald-200' 
                  : 'bg-blue-50 text-blue-700 border-blue-200'
              ]"
            >
              {{ detailUser.role === 'A' ? 'System Administrator' : 'Authorized User' }}
            </span>
          </div>
        </div>

        <div class="space-y-4 text-xs">
          <!-- ID -->
          <div class="flex justify-between border-b border-slate-150 pb-2.5">
            <span class="font-semibold text-slate-500 uppercase tracking-wide">Identifier ID</span>
            <span class="font-mono text-slate-700 font-semibold">{{ detailUser.id }}</span>
          </div>
          <!-- Email -->
          <div class="flex justify-between border-b border-slate-150 pb-2.5">
            <span class="font-semibold text-slate-500 uppercase tracking-wide">Email Address</span>
            <span class="font-mono text-cyan-700 font-semibold">{{ detailUser.email }}</span>
          </div>
          <!-- Location -->
          <div class="space-y-1.5">
            <span class="font-semibold text-slate-500 uppercase tracking-wide block">Primary Location</span>
            <p class="text-slate-650 leading-relaxed bg-slate-50 border border-slate-200 p-3 rounded-xl">
              {{ detailUser.address }}
            </p>
          </div>
        </div>

        <div class="flex justify-end pt-2">
          <button 
            @click="showDetailModal = false"
            class="px-5 py-2 text-xs font-semibold text-slate-700 hover:text-slate-900 bg-slate-100 hover:bg-slate-200 border border-slate-200 rounded-xl transition-all"
          >
            Close Portal
          </button>
        </div>
      </div>
    </BaseModal>
  </div>
</template>

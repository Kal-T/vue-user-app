<!-- STATEFUL PARENT — owns all application state. All child components are stateless — they receive data via props and communicate back via emits only. -->

<script setup lang="ts">
import { ref } from 'vue'
import type { User } from '../types/user'
import UserFormComponent from '../components/UserFormComponent.vue'
import UserListComponent from '../components/UserListComponent.vue'
import UserSummaryComponent from '../components/UserSummaryComponent.vue'
import CountComponent from '../components/CountComponent.vue'
import BaseModal from '../components/BaseModal.vue'

// Fetch environment variable to demonstrate configurations
const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080'

// Seeded refs containing 4 mock users (2 role 'A', 2 role 'U')
const users = ref<User[]>([
  {
    id: 'usr-01',
    name: 'Sarah',
    lastName: 'Connor',
    email: 'sarah.connor@resistance.net',
    role: 'A',
    address: '101 Safehouse Way, Mojave, CA'
  },
  {
    id: 'usr-02',
    name: 'John',
    lastName: 'Connor',
    email: 'john.connor@resistance.net',
    role: 'U',
    address: 'Bunker 12, High Desert, CA'
  },
  {
    id: 'usr-03',
    name: 'Marcus',
    lastName: 'Wright',
    email: 'marcus.wright@projectangel.org',
    role: 'U',
    address: '88 Cyberdyne Cyber Lane, San Francisco, CA'
  },
  {
    id: 'usr-04',
    name: 'T-800',
    lastName: 'Model 101',
    email: 'cyberdyne.systems@cyberdyne.org',
    role: 'A',
    address: '100 Computer History Museum Dr, Mountain View, CA'
  }
])

const selectedUser = ref<User | null>(null)
const filter = ref<'all' | 'admin' | 'user'>('all')
const showDetailModal = ref<boolean>(false)
const detailUser = ref<User | null>(null)

// Method to test interaction with detail modal
const triggerDetailModalDemo = () => {
  detailUser.value = users.value[0]
  selectedUser.value = users.value[0]
  showDetailModal.value = true
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
    <!-- Dashboard Status Tracker (Visualizing the stateful refs) -->
    <div class="grid grid-cols-2 md:grid-cols-5 gap-4 p-5 bg-slate-900/30 border border-slate-800/80 rounded-2xl">
      <div class="space-y-1">
        <span class="block text-xs font-semibold text-slate-500 uppercase tracking-wider">Total Users</span>
        <span class="text-2xl font-bold text-slate-100">{{ users.length }}</span>
      </div>
      <div class="space-y-1">
        <span class="block text-xs font-semibold text-slate-500 uppercase tracking-wider">Active Filter</span>
        <span class="text-2xl font-bold text-violet-400 capitalize">{{ filter }}</span>
      </div>
      <div class="space-y-1 col-span-2">
        <span class="block text-xs font-semibold text-slate-500 uppercase tracking-wider">API Connection</span>
        <span class="text-sm font-mono text-cyan-400 truncate block mt-1">{{ apiUrl }}</span>
      </div>
      <div class="space-y-1 flex flex-col justify-end gap-2">
        <button 
          @click="triggerDetailModalDemo"
          class="w-full py-1.5 px-3 text-[11px] font-semibold text-slate-300 hover:text-white bg-slate-800/80 hover:bg-slate-700/80 border border-slate-750 rounded-xl transition-all duration-300 shadow-md active:scale-95 text-center"
        >
          Demo Modal
        </button>
        <button 
          @click="addMockUser"
          class="w-full py-1.5 px-3 text-[11px] font-semibold text-violet-100 hover:text-white bg-violet-600/80 hover:bg-violet-500/80 border border-violet-500/30 rounded-xl transition-all duration-300 shadow-md active:scale-95 text-center"
        >
          Add Mock User
        </button>
      </div>
    </div>

    <!-- Two-column grid: Left 40% (4 cols), Right 60% (6 cols) -->
    <div class="grid grid-cols-1 lg:grid-cols-10 gap-8">
      <!-- Left Column: UserFormComponent & CountComponent -->
      <div class="lg:col-span-4 space-y-6">
        <UserFormComponent />
        <CountComponent label="Active Users Count" :count="users.length" />
      </div>

      <!-- Right Column: UserSummaryComponent -->
      <div class="lg:col-span-6">
        <UserSummaryComponent :users="users" />
      </div>
    </div>

    <!-- Bottom Row: UserListComponent full width -->
    <div class="w-full">
      <UserListComponent />
    </div>

    <!-- BaseModal (Overlay element showing stateful modal display) -->
    <div 
      v-if="showDetailModal" 
      class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-950/80 backdrop-blur-sm transition-opacity duration-300"
      @click.self="showDetailModal = false"
    >
      <div class="relative w-full max-w-md bg-slate-900 border border-slate-800 rounded-3xl p-1 shadow-2xl">
        <BaseModal />
        <div class="px-6 pb-6 pt-2 flex justify-between items-center bg-slate-900 rounded-b-3xl">
          <div v-if="detailUser" class="text-xs text-slate-400">
            Viewing: {{ detailUser.name }} {{ detailUser.lastName }}
          </div>
          <button 
            @click="showDetailModal = false"
            class="px-4 py-1.5 text-xs font-semibold text-slate-200 bg-red-900/40 hover:bg-red-900/60 border border-red-700/40 rounded-lg transition-all"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

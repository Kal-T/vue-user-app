<!-- STATELESS — localUser is a working copy only. Source of truth lives in parent. -->

<script setup lang="ts">
import { reactive, watch } from 'vue'
import type { User } from '../types/user'

const props = defineProps<{
  user: User | null
  loading: boolean
  error: string | null
}>()

const emit = defineEmits<{
  save: [data: Omit<User, 'id'>]
  update: [data: User]
  reset: []
}>()

// Only allowed local state: working copy for v-model binding
const localUser = reactive({
  id: '',
  name: '',
  lastName: '',
  email: '',
  role: 'U' as 'U' | 'A',
  address: ''
})

// Sync local working state whenever props.user changes
watch(
  () => props.user,
  (newUser) => {
    if (newUser) {
      localUser.id = newUser.id
      localUser.name = newUser.name
      localUser.lastName = newUser.lastName
      localUser.email = newUser.email
      localUser.role = newUser.role
      localUser.address = newUser.address
    } else {
      localUser.id = ''
      localUser.name = ''
      localUser.lastName = ''
      localUser.email = ''
      localUser.role = 'U'
      localUser.address = ''
    }
  },
  { immediate: true }
)

const handleSubmit = () => {
  if (props.user) {
    emit('update', { ...localUser } as User)
  } else {
    emit('save', {
      name: localUser.name,
      lastName: localUser.lastName,
      email: localUser.email,
      role: localUser.role,
      address: localUser.address
    })
  }
}

const handleReset = () => {
  if (props.user) {
    // Restore from props.user if editing
    localUser.id = props.user.id
    localUser.name = props.user.name
    localUser.lastName = props.user.lastName
    localUser.email = props.user.email
    localUser.role = props.user.role
    localUser.address = props.user.address
  } else {
    // Clear if creating
    localUser.id = ''
    localUser.name = ''
    localUser.lastName = ''
    localUser.email = ''
    localUser.role = 'U'
    localUser.address = ''
  }
  emit('reset')
}
</script>

<template>
  <div class="bg-white border border-slate-200/80 rounded-2xl p-6 shadow-sm space-y-6">
    <div>
      <h2 class="text-xl font-bold text-slate-900">
        {{ props.user ? 'Edit Account' : 'Register Account' }}
      </h2>
      <p class="text-xs text-slate-500 mt-1">
        {{ props.user ? 'Modify existing user credentials' : 'Register a new user in the registry' }}
      </p>
    </div>

    <!-- Error Banner -->
    <div 
      v-show="props.error" 
      class="p-4 bg-red-50 border border-red-200 rounded-xl flex items-center gap-3 text-red-700 text-xs font-semibold shadow-sm"
    >
      <svg class="h-4 w-4 shrink-0 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
      </svg>
      <span>{{ props.error }}</span>
    </div>

    <!-- User Form -->
    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div class="grid grid-cols-2 gap-4">
        <!-- First Name -->
        <div class="space-y-1.5">
          <label class="text-xs font-bold text-slate-500 uppercase tracking-wide">First Name</label>
          <input
            v-model="localUser.name"
            type="text"
            required
            placeholder="e.g. John"
            class="w-full bg-white border border-slate-250 hover:border-slate-350 focus:border-violet-500 focus:ring-1 focus:ring-violet-500/20 rounded-xl px-4 py-2 text-sm text-slate-800 outline-none transition-all placeholder:text-slate-400"
          />
        </div>

        <!-- Last Name -->
        <div class="space-y-1.5">
          <label class="text-xs font-bold text-slate-500 uppercase tracking-wide">Last Name</label>
          <input
            v-model="localUser.lastName"
            type="text"
            required
            placeholder="e.g. Connor"
            class="w-full bg-white border border-slate-250 hover:border-slate-350 focus:border-violet-500 focus:ring-1 focus:ring-violet-500/20 rounded-xl px-4 py-2 text-sm text-slate-800 outline-none transition-all placeholder:text-slate-400"
          />
        </div>
      </div>

      <!-- Email -->
      <div class="space-y-1.5">
        <label class="text-xs font-bold text-slate-500 uppercase tracking-wide">Email Address</label>
        <input
          v-model="localUser.email"
          type="email"
          required
          placeholder="e.g. john@resistance.net"
          class="w-full bg-white border border-slate-250 hover:border-slate-350 focus:border-violet-500 focus:ring-1 focus:ring-violet-500/20 rounded-xl px-4 py-2 text-sm text-slate-800 outline-none transition-all placeholder:text-slate-400 font-mono"
        />
      </div>

      <!-- Role Selection -->
      <div class="space-y-1.5">
        <label class="text-xs font-bold text-slate-500 uppercase tracking-wide">System Role</label>
        <div class="grid grid-cols-2 gap-3">
          <button
            type="button"
            @click="localUser.role = 'U'"
            :class="[
              'py-2 text-xs font-semibold border rounded-xl transition-all',
              localUser.role === 'U' 
                ? 'bg-blue-50 border-blue-300 text-blue-700 font-bold shadow-sm shadow-blue-100/50' 
                : 'bg-slate-50 border-slate-200 hover:border-slate-300 text-slate-500'
            ]"
          >
            Regular User
          </button>
          <button
            type="button"
            @click="localUser.role = 'A'"
            :class="[
              'py-2 text-xs font-semibold border rounded-xl transition-all',
              localUser.role === 'A' 
                ? 'bg-emerald-50 border-emerald-300 text-emerald-700 font-bold shadow-sm shadow-emerald-100/50' 
                : 'bg-slate-50 border-slate-200 hover:border-slate-300 text-slate-500'
            ]"
          >
            Administrator
          </button>
        </div>
      </div>

      <!-- Address -->
      <div class="space-y-1.5">
        <label class="text-xs font-bold text-slate-500 uppercase tracking-wide">Mailing Address</label>
        <textarea
          v-model="localUser.address"
          required
          rows="2"
          placeholder="e.g. Bunker 10, High Desert, CA"
          class="w-full bg-white border border-slate-250 hover:border-slate-350 focus:border-violet-500 focus:ring-1 focus:ring-violet-500/20 rounded-xl px-4 py-2 text-sm text-slate-800 outline-none transition-all placeholder:text-slate-400 resize-none"
        ></textarea>
      </div>

      <!-- Actions Panel -->
      <div class="flex items-center justify-end gap-3 pt-2">
        <button
          type="button"
          @click="handleReset"
          :disabled="props.loading"
          class="px-4 py-2 text-xs font-semibold text-slate-500 hover:text-slate-700 disabled:opacity-40 transition-colors"
        >
          {{ props.user ? 'Cancel' : 'Clear' }}
        </button>

        <!-- Save Button (Creating) -->
        <button
          v-if="!props.user"
          type="submit"
          :disabled="props.loading"
          class="px-5 py-2 text-xs font-semibold text-white bg-gradient-to-r from-violet-600 to-indigo-600 hover:from-violet-500 hover:to-indigo-500 rounded-lg shadow-md shadow-violet-100 disabled:opacity-40 disabled:pointer-events-none transition-all duration-300"
        >
          Save User
        </button>

        <!-- Update Button (Editing) -->
        <button
          v-if="props.user"
          type="submit"
          :disabled="props.loading"
          class="px-5 py-2 text-xs font-semibold text-white bg-gradient-to-r from-emerald-600 to-teal-600 hover:from-emerald-500 hover:to-teal-500 rounded-lg shadow-md shadow-emerald-100 disabled:opacity-40 disabled:pointer-events-none transition-all duration-300"
        >
          Update User
        </button>
      </div>
    </form>
  </div>
</template>

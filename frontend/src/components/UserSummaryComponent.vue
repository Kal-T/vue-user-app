<!-- STATELESS — computed values derived from props. Updates automatically when parent's users array changes. -->
<script setup lang="ts">
import { computed } from 'vue'
import type { User } from '../types/user'
import CountComponent from './CountComponent.vue'

const props = defineProps<{
  users: User[]
}>()

// Computed properties derived strictly from props
const totalCount = computed(() => props.users.length)
const adminCount = computed(() => props.users.filter(u => u.role === 'A').length)
const userCount = computed(() => props.users.filter(u => u.role === 'U').length)
</script>

<template>
  <div class="p-6 bg-slate-900/40 backdrop-blur-md border border-slate-800/80 rounded-2xl space-y-6">
    <div>
      <h2 class="text-lg font-bold text-slate-150">Overview Metrics</h2>
      <p class="text-xs text-slate-400 mt-0.5">Live metrics calculated directly from user dataset props</p>
    </div>

    <!-- Render three stateless CountComponents in a responsive grid -->
    <div class="grid grid-cols-1 sm:grid-cols-3 gap-4">
      <CountComponent label="Total Users" :count="totalCount" />
      <CountComponent label="Admin Users" :count="adminCount" />
      <CountComponent label="Regular Users" :count="userCount" />
    </div>
  </div>
</template>

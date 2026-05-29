<!-- STATELESS — pure display component. No state, no logic. -->
<script setup lang="ts">
defineProps<{
  label: string
  count: number
}>()
</script>

<template>
  <div class="relative overflow-hidden p-6 bg-white border border-slate-200/80 rounded-2xl shadow-sm hover:border-slate-350 hover:shadow-md transition-all duration-300 group">
    <!-- Soft background glow on hover -->
    <div class="absolute -inset-px bg-gradient-to-r from-violet-500/5 to-cyan-500/5 rounded-2xl opacity-0 group-hover:opacity-100 transition-opacity duration-500 pointer-events-none" />
    
    <div class="relative space-y-3">
      <!-- Label -->
      <span class="block text-xs font-semibold text-slate-500 uppercase tracking-wider group-hover:text-slate-700 transition-colors">
        {{ label }}
      </span>
      <!-- Count -->
      <div class="text-5xl font-extrabold text-slate-900 tracking-tight">
        {{ count }}
      </div>
    </div>
  </div>
</template>

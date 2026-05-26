<!-- STATELESS — pure display component. No state, no logic. -->
<script setup lang="ts">
defineProps<{
  label: string
  count: number
}>()
</script>

<template>
  <div class="relative overflow-hidden p-6 bg-slate-900/60 backdrop-blur-xl border border-slate-850 rounded-2xl shadow-lg hover:border-slate-700/80 hover:shadow-2xl transition-all duration-300 group">
    <!-- Soft background glow on hover -->
    <div class="absolute -inset-px bg-gradient-to-r from-violet-500/5 to-cyan-500/5 rounded-2xl opacity-0 group-hover:opacity-100 transition-opacity duration-500 pointer-events-none" />
    
    <div class="relative space-y-3">
      <!-- Label -->
      <span class="block text-xs font-semibold text-slate-400 uppercase tracking-wider group-hover:text-slate-300 transition-colors">
        {{ label }}
      </span>
      <!-- Count -->
      <div class="text-5xl font-extrabold text-white tracking-tight">
        {{ count }}
      </div>
    </div>
  </div>
</template>

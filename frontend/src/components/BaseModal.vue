<!-- STATELESS — visibility controlled entirely by parent via show prop. -->

<script setup lang="ts">
defineProps<{
  show: boolean
  title: string
}>()

const emit = defineEmits<{
  close: []
}>()
</script>

<template>
  <Transition
    enter-active-class="transition ease-out duration-300"
    enter-from-class="opacity-0 scale-95"
    enter-to-class="opacity-100 scale-100"
    leave-active-class="transition ease-in duration-200"
    leave-from-class="opacity-100 scale-100"
    leave-to-class="opacity-0 scale-95"
  >
    <div
      v-show="show"
      class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/40 backdrop-blur-sm"
      @click.self="emit('close')"
    >
      <div 
        class="w-full max-w-lg bg-white border border-slate-200/80 rounded-3xl shadow-2xl overflow-hidden transform transition-all flex flex-col"
        role="dialog"
        aria-modal="true"
      >
        <!-- Modal Header -->
        <div class="px-6 py-5 border-b border-slate-150 flex items-center justify-between">
          <h3 class="text-lg font-bold text-slate-900 tracking-wide">{{ title }}</h3>
          <button
            @click="emit('close')"
            class="text-slate-400 hover:text-slate-600 transition-colors p-1"
          >
            <svg class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        <!-- Modal Body Content -->
        <div class="p-6 text-sm text-slate-600 leading-relaxed overflow-y-auto max-h-[70vh]">
          <slot />
        </div>
      </div>
    </div>
  </Transition>
</template>

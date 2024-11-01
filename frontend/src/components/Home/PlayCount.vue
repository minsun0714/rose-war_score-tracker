<script setup lang="ts">
import { useCountUp } from '@/lib/hooks/useCountUp'

const playCount = useCountUp(2475, 500)
</script>

<template>
  <div
    class="flex flex-col justify-center items-center bg-black text-white h-48 gap-y-4"
  >
    <strong class="text-3xl">{{ playCount }}</strong>
    <label class="text-xs">전체 play</label>
  </div>
</template>

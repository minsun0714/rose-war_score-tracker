<script setup lang="ts">
import { gameInfoKey } from '@/lib/types'

const gameInfo = {
  rank: 1,
  maxScore: 500,
  winCount: 150,
  loseCount: 150,
  drawCount: 46,
  playCount: 346,
}
</script>

<template>
  <div class="flex justify-center flex-col border p-4">
    <ul class="grid grid-cols-2 gap-y-4">
      <template v-for="(value, key) in gameInfo" :key="key">
        <li class="text-slate-500 text-sm text-center">
          {{ gameInfoKey[key] }}
        </li>
        <li class="text-center">{{ value }}</li>
      </template>
    </ul>
  </div>
</template>

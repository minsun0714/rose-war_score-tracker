<script setup lang="ts">
import StatApiFacade from '@/api/apiFacade/StatApiFacade';
import { gameInfoKey } from '@/lib/types'

const {data: gameInfo} = StatApiFacade.useFetchStat()
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

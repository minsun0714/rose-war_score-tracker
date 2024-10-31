<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const navigateTo = (name: string) => {
  if (route.path.endsWith('/edit') && name === "myInfo") {
    return
  }
  router.push({ name })
}
</script>

<template>
  <div class="flex justify-center flex-col gap-4 p-6">
    <h1 class="text-xl text-center">마이페이지</h1>
    <div class="flex justify-start gap-4 border-b">
      <button
        @click="navigateTo('gameInfo')"
        class="p-2 border border-transparent"
        :class="{
          'border-t-purple font-bold': $route.path === '/mypage/gameinfo',
        }"
      >
        게임
      </button>
      <button
        @click="navigateTo('myInfo')"
        class="p-2 border border-transparent"
        :class="{
          'border-t-purple font-bold': $route.path.startsWith('/mypage/myinfo'),
        }"
      >
        내 정보
      </button>
    </div>
    <router-view />
  </div>
</template>

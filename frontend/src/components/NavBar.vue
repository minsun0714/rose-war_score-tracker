<script setup lang="ts">
import AuthApiFacade from '@/api/apiFacade/AuthApiFacade'
import ProfileImg from '@/assets/Male User.svg'
import { useRouter } from 'vue-router'
import { computed } from 'vue'
import { useAccordionStore } from '@/stores/counter'
import NavAccordion from './common/NavAccordion.vue'

const router = useRouter()

const { data } = AuthApiFacade.useFetchUserInfo()

const userInfo = computed(() => data?.value || null)

const logout = () => {
  localStorage.removeItem('token')
  router.push('/').then(() => {
    window.location.reload()
  })
}

const accordionStore = useAccordionStore()
</script>

<template>
  <header class="border-b">
    <nav class="h-16 flex flex-row justify-between">
      <div class="flex flex-row items-center justify-center gap-4 p-4">
        <img src="../assets/Menu.svg" @click="accordionStore.toggleAccordion" />
        <RouterLink to="/"
          ><img src="../assets/Tudor Rose.svg" class="min-w-8"
        /></RouterLink>
      </div>
      <div>
        <div
          v-if="userInfo"
          class="h-full flex flex-row items-center justify-center gap-1 p-2 text-xs whitespace-nowrap"
        >
          <img :src="userInfo?.profileImg || ProfileImg" alt="" />
          <p>{{ userInfo?.nickname }}</p>
          <button @click="logout">로그아웃</button>
        </div>
        <div
          v-else
          class="h-full flex flex-row items-center justify-center gap-2 p-4 text-xs"
        >
          <RouterLink to="/auth/signup">회원가입</RouterLink>
          <RouterLink to="/auth/login">로그인</RouterLink>
        </div>
      </div>
    </nav>
    <NavAccordion />
  </header>
</template>

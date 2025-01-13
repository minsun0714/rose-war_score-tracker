<script setup lang="ts">
import LoginForm from '@/components/Auth/LoginForm.vue'
</script>

<template>
  <main
    class="flex flex-col justify-center items-center mobile:max-w-[320px] mx-auto"
  >
    <h1 class="h-20 flex items-center">로그인</h1>
    <LoginForm />
  </main>
</template>

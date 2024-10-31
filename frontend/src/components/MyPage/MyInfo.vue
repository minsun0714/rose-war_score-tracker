<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { MyInfoKey } from '@/lib/types'
import ProfilePic from '../../assets/Male User.svg'
import SignatureBtn from '../common/SignatureBtn.vue'
import { ref, watch } from 'vue'

const router = useRouter()
const route = useRoute()
const myInfo = {
  profilePic: ProfilePic,
  name: '이민선',
  id: 'minsun',
  nickname: 'minsun',
}

const isEditing = ref(false)

watch(
  () => route.path,
  newPath => {
    isEditing.value = newPath.endsWith('edit')
  },
  { immediate: true },
)

const navigateTo = (name: string) => {
  if (name === 'editMyInfo') {
    isEditing.value = true
  } else {
    isEditing.value = false
  }
  router.push({ name })
}
</script>

<template>
  <div v-if="!isEditing">
    <div class="flex justify-center flex-col border p-4">
      <ul class="grid grid-cols-2 gap-y-4 text-sm">
        <template v-for="(value, key) in myInfo" :key="key">
          <li
            class="flex justify-center items-center text-slate-700 text-sm text-center"
          >
            {{ MyInfoKey[key] }}
          </li>
          <li class="flex justify-center">
            <img :src="value" alt="프로필 사진" v-if="key === 'profilePic'" />
            <span v-else>{{ value }}</span>
          </li>
        </template>
      </ul>
    </div>
    <div class="flex flex-row justify-between items-start py-6">
      <p class="text-sm text-slate-500">회원탈퇴</p>
      <SignatureBtn text="수정" @click="navigateTo('editMyInfo')" />
    </div>
  </div>
  <div v-if="isEditing">
    <router-view />
  </div>
</template>

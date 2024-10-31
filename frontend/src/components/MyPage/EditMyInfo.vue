<script setup lang="ts">
import { MyInfoKey } from '@/lib/types'
import ProfilePic from '../../assets/Male User.svg'
import SignatureBtn from '../common/SignatureBtn.vue'
const myInfo = {
  profilePic: ProfilePic,
  name: '이민선',
  id: 'minsun',
  nickname: 'minsun',
}
</script>

<template>
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
    <SignatureBtn text="완료" />
  </div>
</template>

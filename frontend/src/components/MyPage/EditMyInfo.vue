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
  수정 페이지 입니다.
  <div class="flex flex-col justify-between items-start py-6">
    <div class="flex flex-row justify-center gap-2">
      <SignatureBtn text="취소" /> <SignatureBtn text="완료" />
    </div>
    <p class="text-sm text-slate-500">회원탈퇴</p>
  </div>
</template>

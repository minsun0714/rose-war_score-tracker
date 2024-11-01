<script setup lang="ts">
import ProfilePic from '../../assets/Male User.svg'
import SignatureBtn from '../common/SignatureBtn.vue'
import { ref } from 'vue'

const imageFile = ref<File | null>(null)
const imageUrl = ref<string>(ProfilePic) // 이미지 URL 상태 추가

const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    imageFile.value = target.files[0]
    imageUrl.value = URL.createObjectURL(imageFile.value) // 선택한 파일의 URL 생성
  }
}
</script>

<template>
  <div class="flex flex-col justify-between items-start py-6">
    <div class="flex flex-col justify-center items-center border w-full">
      <label>프로필 사진</label>
      <div class="mt-2">
        <img
          :src="imageUrl"
          alt="미리보기"
          class="w-8 h-8 object-cover rounded-full"
        />
      </div>
      <!-- 파일 입력 숨기기 -->
      <input
        type="file"
        @change="handleFileChange"
        class="hidden"
        id="fileInput"
      />
      <label
        for="fileInput"
        class="w-full cursor-pointer text-center underline text-slate-400 text-sm"
      >
        파일 선택
      </label>
      <span v-if="imageFile?.name" class="mt-1 text-sm text-gray-600">{{
        imageFile?.name
      }}</span>
    </div>
    <div class="flex flex-row justify-center gap-2 mt-4">
      <SignatureBtn text="완료" />
    </div>
    <p class="text-sm text-slate-500">회원탈퇴</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import AuthApiFacade from "@/api/apiFacade/AuthApiFacade.ts"
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from '@/components/ui/accordion'
import { accordionItems } from '@/lib/constants'
import ProfileImg from "@/assets/Male User.svg"

const { data: userInfo, isSuccess } = AuthApiFacade.useFetchUserInfo()

const isAccordionOpen = ref(false)
const activeItem = ref('') // 현재 열려 있는 아이템의 value를 저장

function toggleAccordion() {
  isAccordionOpen.value = !isAccordionOpen.value
}

function setActiveItem(value: string) {
  activeItem.value = value
}

function logout() {
  localStorage.removeItem('token')
}

</script>

<template>
  <header class="border-b">
    <nav class="h-16 flex flex-row justify-between">
      <div class="flex flex-row items-center justify-center gap-2 p-4 text-xs">
        <img src="../assets/Menu.svg" @click="toggleAccordion" />
        <RouterLink to="/"><img src="../assets/Tudor Rose.svg" /></RouterLink>
        <span v-text="userInfo?.nickname + '님'" v-if="isSuccess"></span>
      </div>
      <div class="flex justify-center items-center p-2 text-xs" v-if="isSuccess">
        <span @click="logout">로그아웃</span>
        <span><img :src="userInfo?.profileImg || ProfileImg"/></span>
      </div>
      <div class="flex flex-row items-center justify-center gap-x-2 p-2 text-xs" v-else>
        <RouterLink to="/auth/signup">회원가입</RouterLink>
        <RouterLink to="/auth/login">로그인</RouterLink>
      </div>
    </nav>
    <nav v-if="isAccordionOpen" class="flex flex-col">
      <Accordion type="single" class="w-full" collapsible>
        <AccordionItem
          v-for="item in accordionItems"
          :key="item.value"
          :value="item.value"
        >
          <AccordionTrigger
            :class="{ 'bg-purple text-white': activeItem === item.value }"
            @click="setActiveItem(item.value)"
          >
            {{ item.title }}
          </AccordionTrigger>
          <AccordionContent>
            <ul
              v-if="item.children"
              class="h-full flex flex-col justify-end gap-y-1 mt-3"
            >
              <RouterLink
                v-for="child in item.children"
                :key="child.value"
                :to="child.path"
              >
                <li :class="{ 'hover:underline': activeItem === item.value }">
                  {{ child.title }}
                </li>
              </RouterLink>
            </ul>
          </AccordionContent>
        </AccordionItem>
      </Accordion>
    </nav>
  </header>
</template>

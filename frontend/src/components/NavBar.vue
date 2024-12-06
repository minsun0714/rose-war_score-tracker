<script setup lang="ts">
import { ref } from 'vue'
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from '@/components/ui/accordion'
import { accordionItems } from '@/lib/constants'
import AuthApiFacade from '@/api/apiFacade/AuthApiFacade'
import ProfileImg from '@/assets/Male User.svg'
import { useRouter } from 'vue-router'
import { computed } from 'vue'

const router = useRouter()

const { data } = AuthApiFacade.useFetchUserInfo()

// 컴포넌트에서 사용하는 유저 정보 (리액티브 데이터)
const userInfo = computed(() => data?.value || null)

const logout = () => {
  localStorage.removeItem('token')
  router.push('/').then(() => {
    window.location.reload()
  })
}

const isAccordionOpen = ref(false)
const activeItem = ref('') // 현재 열려 있는 아이템의 value를 저장

function toggleAccordion() {
  isAccordionOpen.value = !isAccordionOpen.value
}

function setActiveItem(value: string) {
  activeItem.value = value
}
</script>

<template>
  <header class="border-b">
    <nav class="h-16 flex flex-row justify-between">
      <div class="flex flex-row items-center justify-center gap-4 p-4">
        <img src="../assets/Menu.svg" @click="toggleAccordion" />
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

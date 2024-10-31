<script setup lang="ts">
import { ref } from 'vue'
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from '@/components/ui/accordion'

const isAccordionOpen = ref(false)
const activeItem = ref('') // 현재 열려 있는 아이템의 value를 저장

const accordionItems = [
  {
    value: 'item-1',
    title: 'Play',
    children: [{ value: 'item-1-1', title: '점수계산', path: '/play' }],
  },
  {
    value: 'item-2',
    title: 'Ranking',
    children: [{ value: 'item-2-1', title: '랭킹', path: '/ranking' }],
  },
  {
    value: 'item-3',
    title: 'Talk',
    children: [
      { value: 'item-3-1', title: '전체글', path: '/board/list' },
      { value: 'item-3-2', title: '글쓰기', path: '/board/write' },
    ],
  },
]

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
        <RouterLink to="/"><img src="../assets/Tudor Rose.svg" /></RouterLink>
      </div>
      <div class="flex flex-row items-center justify-center gap-4 p-4">
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
              class="h-12 flex flex-col justify-around gap-y-3"
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

<script setup lang="ts">
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from '@/components/ui/accordion'
import { accordionItems } from '@/lib/constants'
import { useAccordionStore } from '@/stores/accordion'

const accordionStore = useAccordionStore()
</script>

<template>
  <nav v-if="accordionStore.isAccordionOpen" class="flex flex-col">
    <Accordion type="single" class="w-full" collapsible>
      <AccordionItem
        v-for="item in accordionItems"
        :key="item.value"
        :value="item.value"
      >
        <AccordionTrigger
          :class="{
            'bg-purple text-white': accordionStore.activeItem === item.value,
          }"
          @click="accordionStore.setActiveItem(item.value)"
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
              <li
                :class="{
                  'hover:underline': accordionStore.activeItem === item.value,
                }"
              >
                {{ child.title }}
              </li>
            </RouterLink>
          </ul>
        </AccordionContent>
      </AccordionItem>
    </Accordion>
  </nav>
</template>

<script setup lang="ts">
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselPrevious,
} from '@/components/ui/carousel'
import { Card, CardContent } from '@/components/ui/card'
import carouselItems from '@/assets/carousel'

</script>

<template>
  <Carousel class="relative w-full border">
    <CarouselContent class="flex items-center">
      <CarouselItem v-for="(item, index) in carouselItems" :key="index">
        <ul>
          <Card>
            <CardContent class="flex items-center justify-center">
              <li>
                <img :src="item" alt="carousel item" class="h-44" />
              </li>
            </CardContent>
          </Card>
        </ul>
      </CarouselItem>
    </CarouselContent>
    <CarouselPrevious />
  </Carousel>
</template>

<script setup lang="ts">
import { useCountUp } from '@/lib/hooks/useCountUp'
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselPrevious,
} from '@/components/ui/carousel'
import carouselItems from '@/assets/carousel'

const playCount = useCountUp(2475, 500)
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

  <div class="flex flex-col items-center justify-center h-48 border gap-y-4">
    <h1 class="font-bold">Rose War score tracker</h1>
    <div class="w-48">
      <p class="text-xs text-center">
        인기 보드게임 장미전쟁을 play하고 게임 결과대로 보드판을 클릭하여 점수를
        자동으로 계산할 수 있는 곳입니다.
      </p>
    </div>
  </div>

  <div
    class="flex flex-col justify-center items-center bg-black text-white h-48 gap-y-4"
  >
    <strong class="text-3xl">{{ playCount }}</strong>
    <label class="text-xs">전체 play</label>
  </div>
</template>

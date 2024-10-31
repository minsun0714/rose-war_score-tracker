<script setup lang="ts">
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselPrevious,
} from '@/components/ui/carousel'
import { useCountUp } from '@/lib/hooks/useCountUp'

const playCount = useCountUp(2475, 500)
</script>

<template>
  <Carousel class="relative w-full border">
    <CarouselContent>
      <CarouselItem v-for="(_, index) in 3" :key="index">
        <div class="p-1">
          <Card>
            <CardContent
              class="flex aspect-square items-center justify-center p-6"
            >
              <img src="../assets/Male User.svg" />
            </CardContent>
          </Card>
        </div>
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

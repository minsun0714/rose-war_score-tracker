import { ref, onMounted } from 'vue'

export function useCountUp(targetNumber: number, duration: number = 2000) {
  const count = ref(0)

  onMounted(() => {
    const increment = targetNumber / (duration / 20)

    const interval = setInterval(() => {
      if (count.value < targetNumber) {
        count.value += Math.ceil(increment)
      } else {
        count.value = targetNumber
        clearInterval(interval)
      }
    }, 20)
  })

  return count
}

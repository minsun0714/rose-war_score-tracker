import { useQuery } from '@tanstack/vue-query'
import StatService from '../services/StatService'

class StatApiFacade {
  static useFetchStat() {
    return useQuery({
      queryKey: ['stat'],
      queryFn: () => StatService.getStat(),
    })
  }
}

export default StatApiFacade

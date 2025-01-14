import api from '../apiClient'

class StatService {
  // Private 메서드로 API 호출 로직 관리
  private static async _getStat(): Promise<boolean> {
    const response = await api.get(`/api/stats`)
    return response.data
  }

  // Public 메서드로 외부에서 사용할 수 있도록 제공
  static getStat(): Promise<boolean> {
    return this._getStat()
  }
}

export default StatService

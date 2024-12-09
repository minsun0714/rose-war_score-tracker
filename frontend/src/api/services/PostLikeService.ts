import api from '../apiClient'

class PostService {
  // Private 메서드로 API 호출 로직 관리
  static async _getLikeStatus(postId:number): Promise<boolean>{
    const response = await api.get(`/api/likes/${postId}`)
    return response.data
  }
  static async _togglePostLike(postId: number): Promise<boolean> {
    const response = await api.patch(`/api/likes/${postId}`)
    return response.data
  }

  // Public 메서드로 외부에서 사용할 수 있도록 제공
  static getLikeStatus(postId: number): Promise<boolean> {
    return this._getLikeStatus(postId)
  }

  static togglePostLike(postId: number): Promise<boolean> {
    return this._togglePostLike(postId)
  }
}

export default PostService

import api from '../apiClient'
import type { CommentRequest } from '../interface/request'

class CommentService {
  // Private 메서드로 API 호출 로직 관리
  static async _createComment(
    postId: number,
    content: string,
  ): Promise<CommentResponse> {
    const response = await api.post(`/api/posts/${postId}/comments`, {
      content,
    })
    return response.data
  }

  static async _fetchCommentList(postId: number): Promise<CommentResponse[]> {
    const response = await api.get(`/api/posts/${postId}/comments`)
    return response.data
  }

  static async _updateComment(
    postId: number,
    commentId: number,
    updatedComment: CommentRequest,
  ): Promise<CommentResponse> {
    const response = await api.put(
      `/api/posts/${postId}/comments/${commentId}`,
      updatedComment,
    )
    return response.data
  }

  static async _deleteComment(
    postId: number,
    commentId: number,
  ): Promise<void> {
    await api.delete(`/api/posts/${postId}/comments/${commentId}`)
  }

  // Public 메서드로 외부에서 사용할 수 있도록 제공
  static createComment(
    postId: number,
    content: string,
  ): Promise<CommentResponse> {
    return this._createComment(postId, content)
  }

  static fetchCommentList(postId: number): Promise<CommentResponse[]> {
    return this._fetchCommentList(postId)
  }

  static updateComment(
    postId: number,
    commentId: number,
    updatedComment: CommentRequest,
  ): Promise<CommentResponse> {
    return this._updateComment(postId, commentId, updatedComment)
  }

  static deleteComment(postId: number, commentId: number): Promise<void> {
    return this._deleteComment(postId, commentId)
  }
}

export default CommentService

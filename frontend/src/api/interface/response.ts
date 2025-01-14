import type { Post, PostLike, User } from './common'

export interface UserResponse extends User {
  userId: string
}

export interface SignUpResponse extends UserResponse {
  accessToken: string
}

export interface LoginResponse {
  grantType: 'Bearer'
  accessToken: string
}

export interface UpdateUserInfoResponse extends UserResponse {}

export interface PostResponse extends Post {
  postId: string
  writer: UserResponse
  createdAt: Date
  updatedAt: Date
  likeCount: number
  commentCount: number
}

export interface PostLikeResponse {
  result: boolean
}

interface PagedPostResponse {
  content: PostResponse[]
  currentPage: number
  pageSize: number
  totalElements: number
  totalPages: number
  last: boolean
}

export interface CommentResponse extends Comment {
  commentId: number
  postId: number
  writer: UserResponse
  content: string
  createdAt: Date
  updatedAt: Date
  likeCount: number
  childrenComments: CommentResponse[]
}

export interface CommentResponseWithCount {
  topLevelComments: CommentResponse[]
  totalComments: number
}

interface GameResultResponse {
  gameId: number
  player1: UserResponse
  player2: UserResponse
  score1: number
  score2: number
}

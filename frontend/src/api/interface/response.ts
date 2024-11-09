interface UserResponse extends User {
  userId: string
}

interface SignUpResponse extends UserResponse {
  accessToken: string
}

interface UpdateUserInfoResponse extends UserResponse {}

interface PostResponse extends Post {
  postId: string
  writer: UserResponse
  createdAt: Date
  updatedAt: Date
  likeCount: number
}

interface PagedPostResponse {
  content: PostResponse[]
  currentPage: number
  pageSize: number
  totalElements: number
  totalPages: number
  last: boolean
}

interface CommentResponse extends Comment {
  commentId: number
  postId: number
  writer: UserResponse
  content: string
  createdAt: Date
  updatedAt: Date
  likeCount: number
  childrenComments: CommentResponse[]
}

interface GameResultResponse {
  gameId: number
  player1: UserResponse
  player2: UserResponse
  score1: number
  score2: number
}

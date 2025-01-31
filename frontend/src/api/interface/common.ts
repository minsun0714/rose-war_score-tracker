// 리소스 단위
export interface User {
  name: string
  nickname: string
  profileImg: string
}

export interface Auth {
  userId: string
  password: string
  passwordConfirm: string
}

// Post
export interface Post {
  title: string
  content: string
}

export interface PostLike {
  postId: number
}

// Comment
export interface Comment {
  commentId: number
  postId: number
  content: string
  parentId?: number
}

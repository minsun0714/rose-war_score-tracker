// 리소스 단위
export interface User {
  name: string
  nickname: string
  profileImg: string
}

interface Auth {
  userId: string
  password: string
  passwordConfirm: string
}

// Post
interface Post {
  title: string
  content: string
}

// Comment
export interface Comment {
  postId: number
  content: string
  parentId?: number
}

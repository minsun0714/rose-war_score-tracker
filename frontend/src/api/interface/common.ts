// 리소스 단위
interface User {
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
interface Comment {
  content: string
  parentId?: number
}

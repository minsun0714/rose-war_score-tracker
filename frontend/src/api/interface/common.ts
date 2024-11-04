// 리소스 단위
interface User {
    userId: string,
    name: string,
    nickname: string
}

// Post
interface Post {
    title: string,
    content: string
}

// Comment
interface Comment {
    content: string
    parentId?: number
}
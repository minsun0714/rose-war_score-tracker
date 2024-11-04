interface UserResponse extends User {
    profileImg: string
}

interface PostResponse extends Post{
    postId: string
    writer: UserResponse
    createdAt: Date
    updatedAt: Date
    likeCount: number
}

interface CommentResponse extends Comment{
    commentId: number
    postId: number
    writer: UserResponse
    content: string
    createdAt: Date,
    updatedAt: Date,
    likeCount: number
    childrenComments: CommentResponse[]
}

interface GameResultResponse {
    gameId: number,
    player1: UserResponse,
    player2: UserResponse,
    score1: number,
    score2: number
}
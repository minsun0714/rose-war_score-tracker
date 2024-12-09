import type { Comment, PostLike } from './common'

// User
interface SignUpRequest extends Omit<User, 'profileImg'>, Auth {}

interface LoginRequest extends Omit<Auth, 'passwordConfirm'> {}

interface UpdateUserInfoRequest
  extends Omit<User, 'name'>,
    Omit<Auth, 'userId'> {}

// Post
interface PostRequest extends Post {}

// PostLike
export interface PostLikeRequest extends PostLike {}

// Comment
export interface CommentCreateRequest extends Omit<Comment, 'commentId'> {}

export interface CommentUpdateRequest extends Comment{}

export interface CommentDeleteRequest extends Omit<Comment, 'content'> {}

// GameResult
type GameBoard = number[][]
type PlayerId = string | 'guest'

interface GameResultRequest {
  player1Id: PlayerId
  player2Id: PlayerId
  gameBoard: GameBoard
}

function isValidGameBoard(gameBoard: GameBoard): gameBoard is GameBoard {
  return gameBoard.length === 10 && gameBoard.every(row => row.length === 10)
}

// User
interface SignUpRequest extends Omit<User, 'profileImg'>, Auth {}

interface LoginRequest extends Omit<Auth, 'passwordConfirm'> {}

interface UpdateMyInfoRequest
  extends Omit<User, 'name'>,
    Omit<Auth, 'userId'> {}

// Post
interface PostRequest extends Post {
  userId: string
}

// Comment
interface CommentRequest extends Comment {
  userId: string
}

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

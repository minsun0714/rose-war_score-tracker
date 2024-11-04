// User
interface SignUpRequest extends User{
    password: string,
}

interface UpdateMyInfoRequest extends SignUpRequest{
    confirmPassword: string,
    profileImg: string
}

// Post
interface PostRequest extends Post{
    userId: string,
}

// Comment
interface CommentRequest extends Comment{
    userId: string
}

// GameResult
type GameBoard = number[][]
type PlayerId = string | "guest"

interface GameResultRequest {
    player1Id: PlayerId,
    player2Id: PlayerId
    gameBoard: GameBoard
}

function isValidGameBoard(gameBoard: GameBoard): gameBoard is GameBoard {
    return gameBoard.length === 10 && gameBoard.every(row => row.length === 10);
}


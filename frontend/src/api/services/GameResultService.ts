import api from '../apiClient';

class GameResultService {
    // Private 메서드로 API 호출 로직 관리
    static async _createGameResult(player1Id: PlayerId, player2Id: PlayerId, gameBoard: number[][]): Promise<GameResultResponse> {
      console.log(player1Id, player2Id, gameBoard,)
      const response = await api.post('/api/games', { player1Id, player2Id, gameBoard });
        return response.data;
    }

    // Public 메서드로 외부에서 사용할 수 있도록 제공
    static createGameResult(player1Id: PlayerId, player2Id: PlayerId, gameBoard: number[][]): Promise<GameResultResponse> {
        return this._createGameResult(player1Id, player2Id, gameBoard);
    }
}

export default GameResultService;


import { useMutation } from '@tanstack/vue-query';
import GameResultService from '../services/GameResultService';

class GameResultApiFacade {
    // 게임 결과 관련 메서드
    static useCreateGameResult() {
      return useMutation({
        mutationFn: ({ player1Id, player2Id, gameBoard }: GameResultRequest) => GameResultService.createGameResult(player1Id, player2Id, gameBoard),
        onSuccess: (response) => alert(response.score1 + response.score2),
        onError: (error) => console.error(error)
      });
    }
}

export default GameResultApiFacade;

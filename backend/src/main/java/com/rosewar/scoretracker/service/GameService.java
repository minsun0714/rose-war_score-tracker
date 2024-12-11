package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Game;
import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.request.GameResultRequestDTO;
import com.rosewar.scoretracker.dto.response.GameResultResponseDTO;
import com.rosewar.scoretracker.repository.GameRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

import static com.rosewar.scoretracker.security.SecurityUtil.getAuthenticatedMemberId;
import static com.rosewar.scoretracker.security.SecurityUtil.getAuthenticatedMemberIdOrCreateGuest;
import static com.rosewar.scoretracker.util.DTOMapper.toUserInfoDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final StatService statService;

    // 게임 생성
    @Transactional
    public GameResultResponseDTO createResult(GameResultRequestDTO gameRequestDTO) {
        String player1Id = getAuthenticatedMemberIdOrCreateGuest();

        Player player1 = getUserOrCreateGuest(player1Id); // 로그인한 경우에는 실제 userId, 그렇지 않은 경우에는 UUID 부여
        Player player2 = createGuestUser();


        int[][] gameBoard = gameRequestDTO.getGameBoard();
        int[] scores = getScores(gameBoard);

        Game game = Game.builder()
                .player1(player1)
                .player2(player2)
                .score1(scores[0])
                .score2(scores[1])
                .build();

        if (!player1.getUserId().startsWith("guest-")){
            statService.updateStat(player1.getUserId(), scores[0], scores[0] > scores[1]);
        }

        Game savedGame = gameRepository.save(game);
        return toGameResponseDTO(savedGame);
    }

    // 회원 여부를 확인하고 비회원인 경우 임시 User 객체 생성하는 헬퍼 메서드
    private Player getUserOrCreateGuest(String playerId) {
        return userRepository.findById(playerId).orElseGet(this::createGuestUser);
    }

    // 비회원(게스트) 사용자를 위한 임시 User 생성 메서드
    private Player createGuestUser() {
        Player guestPlayer = new Player();
        guestPlayer.setUserId("guest-" + UUID.randomUUID()); // 랜덤 ID 생성
        guestPlayer.setName("Guest");
        guestPlayer.setNickname("Guest-" + UUID.randomUUID().toString().substring(0, 5)); // 랜덤 닉네임
        userRepository.save(guestPlayer);

        return guestPlayer;
    }

    // Game을 GameResponseDTO로 변환하는 헬퍼 메서드
    private GameResultResponseDTO toGameResponseDTO(Game game) {
        return GameResultResponseDTO.builder()
                .gameId(game.getGameId())
                .player1(toUserInfoDTO(game.getPlayer1()))
                .player2(toUserInfoDTO(game.getPlayer2()))
                .score1(game.getScore1())
                .score2(game.getScore2())
                .build();
    }

    // 게임 점수 계산 (임시로 점수 반환)
    private int[] getScores(int[][] gameBoard){
        int player1Score = 0; // player1의 점수
        int player2Score = 0; // player2의 점수

        boolean[][] visited = new boolean[10][10];

        // 상하좌우 이동을 위한 방향 배열
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 10;i++){
            for (int j = 0;j < 10;j++){
                int totalDepth = 0;

                if (!visited[i][j] && gameBoard[i][j] != 0){
                    Stack<int[]> stack = new Stack<>();
                    stack.push(new int[]{i, j, 1});
                    visited[i][j] = true;

                    while (!stack.empty()){
                        int[] cell = stack.pop();

                        totalDepth = cell[2];

                        for (int k=0 ; k< 4; k++){
                            int nx = cell[0] + dx[k], ny = cell[1] + dy[k];

                            if (nx < 0 || ny < 0 || nx >= 10 || ny >= 10) continue;

                            if (visited[nx][ny]) continue;

                            if (gameBoard[nx][ny] != gameBoard[cell[0]][cell[1]]) continue;

                            visited[nx][ny] = true;
                            stack.push(new int[]{nx, ny, cell[2] + 1});

                        }
                    }
                }
                int score = totalDepth * totalDepth;
                if (gameBoard[i][j] == 1) player1Score += score;
                else player2Score += score;
            }
        }
        return new int[]{player1Score, player2Score};
    }
}

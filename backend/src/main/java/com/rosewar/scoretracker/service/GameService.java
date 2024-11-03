package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Game;
import com.rosewar.scoretracker.domain.User;
import com.rosewar.scoretracker.dto.request.GameResultRequestDTO;
import com.rosewar.scoretracker.dto.response.GameResultResponseDTO;
import com.rosewar.scoretracker.repository.GameRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.rosewar.scoretracker.util.DTOMapper.toUserInfoDTO;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final StatService statService;

    public GameService(GameRepository gameRepository, UserRepository userRepository, StatService statService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.statService = statService;
    }

    // 게임 생성
    @Transactional
    public GameResultResponseDTO createResult(GameResultRequestDTO gameRequestDTO) {
        User player1 = getUserOrCreateGuest(gameRequestDTO.getPlayer1Id());
        User player2 = getUserOrCreateGuest(gameRequestDTO.getPlayer2Id());

        Game game = new Game();
        game.setPlayer1(player1);
        game.setPlayer2(player2);

        int[][] gameBoard = gameRequestDTO.getGameBoard();
        int[] scores = getScores(gameBoard);

        game.setScore1(scores[0]);
        game.setScore2(scores[1]);

        Game savedGame = gameRepository.save(game);
        statService.updateStat(player1.getUserId(), scores[0], scores[0] > scores[1]);
        statService.updateStat(player2.getUserId(), scores[1], scores[1] > scores[0]);
        return toGameResponseDTO(savedGame);
    }

    // 회원 여부를 확인하고 비회원인 경우 임시 User 객체 생성하는 헬퍼 메서드
    private User getUserOrCreateGuest(String playerId) {
        return userRepository.findById(playerId).orElseGet(this::createGuestUser);
    }

    // 비회원(게스트) 사용자를 위한 임시 User 생성 메서드
    private User createGuestUser() {
        User guestUser = new User();
        guestUser.setUserId("guest-" + UUID.randomUUID()); // 랜덤 ID 생성
        guestUser.setName("Guest");
        guestUser.setNickname("Guest-" + UUID.randomUUID().toString().substring(0, 5)); // 랜덤 닉네임

        return guestUser;
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
        return new int[]{100, 200};
    }
}

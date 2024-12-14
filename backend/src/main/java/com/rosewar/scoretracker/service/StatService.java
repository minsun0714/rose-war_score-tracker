package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Stat;
import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.response.StatResponseDTO;
import com.rosewar.scoretracker.event.UserCreatedEvent;
import com.rosewar.scoretracker.repository.StatRepository;
import com.rosewar.scoretracker.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static com.rosewar.scoretracker.util.DTOMapper.toStatResponseDTO;

@Service
@Transactional
public class StatService {

    private final StatRepository statRepository;
    private final UserRepository userRepository;

    public StatService(StatRepository statRepository, UserRepository userRepository) {
        this.statRepository = statRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        String userId = event.getUserId();
        createStat(userId); // 통계 생성 로직 호출
    }

    // 특정 사용자의 통계 조회
    public StatResponseDTO getStatByUserId(String userId) {
        Stat stat = statRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Stat not found for user: " + userId));
        return toStatResponseDTO(stat);
    }

    // 특정 사용자의 통계 초기 생성
    @Transactional
    public StatResponseDTO createStat(String userId) {
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Stat stat = Stat.builder()
                .player(player)
                .maxScore(0)
                .totalPlayCount(0)
                .winCount(0)
                .failCount(0)
                .build();

        Stat savedStat = statRepository.save(stat);
        return toStatResponseDTO(savedStat);
    }

    // 특정 사용자의 통계 업데이트 (예: 승리, 패배, 최고 점수 업데이트)
    @Transactional
    public StatResponseDTO updateStat(String userId, int player1Score, int player2Score) {
        Stat stat = statRepository.findByIdWithLock(userId)
                .orElseThrow(() -> new IllegalArgumentException("Stat not found for user: " + userId));

        stat.setTotalPlayCount(stat.getTotalPlayCount() + 1);
        stat.setMaxScore(Math.max(stat.getMaxScore(), player1Score));

        if (player1Score > player2Score) {
            stat.setWinCount(stat.getWinCount() + 1);
        } else if (player1Score < player2Score) {
            stat.setFailCount(stat.getFailCount() + 1);
        }

        Stat updatedStat = statRepository.save(stat);
        return toStatResponseDTO(updatedStat);
    }
}

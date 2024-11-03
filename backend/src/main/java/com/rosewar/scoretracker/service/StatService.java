package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Stat;
import com.rosewar.scoretracker.domain.User;
import com.rosewar.scoretracker.dto.response.StatResponseDTO;
import com.rosewar.scoretracker.repository.StatRepository;
import com.rosewar.scoretracker.repository.UserRepository;

import jakarta.transaction.Transactional;
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

    // 특정 사용자의 통계 조회
    public StatResponseDTO getStatByUserId(String userId) {
        Stat stat = statRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Stat not found for user: " + userId));
        return toStatResponseDTO(stat);
    }

    // 특정 사용자의 통계 초기 생성
    @Transactional
    public StatResponseDTO createStat(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Stat stat = new Stat();
        stat.setUser(user);
        stat.setMaxScore(0);
        stat.setTotalPlayCount(0);
        stat.setWinCount(0);
        stat.setFailCount(0);

        Stat savedStat = statRepository.save(stat);
        return toStatResponseDTO(savedStat);
    }

    // 특정 사용자의 통계 업데이트 (예: 승리, 패배, 최고 점수 업데이트)
    @Transactional
    public StatResponseDTO updateStat(String userId, int newScore, boolean isWin) {
        Stat stat = statRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Stat not found for user: " + userId));

        stat.setTotalPlayCount(stat.getTotalPlayCount() + 1);

        if (isWin) {
            stat.setWinCount(stat.getWinCount() + 1);
            stat.setMaxScore(Math.max(stat.getMaxScore(), newScore));
        } else {
            stat.setFailCount(stat.getFailCount() + 1);
        }

        Stat updatedStat = statRepository.save(stat);
        return toStatResponseDTO(updatedStat);
    }


}

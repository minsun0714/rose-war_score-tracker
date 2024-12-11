package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.domain.Ranking;
import com.rosewar.scoretracker.repository.RankingRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRanking(String userId) {
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Ranking ranking = Ranking.builder()
                .userId(userId)
                .stat(player.getStat())
                .ranking(0)
                .updatedAt(LocalDateTime.now())
                .build();

        rankingRepository.save(ranking);
    }
}

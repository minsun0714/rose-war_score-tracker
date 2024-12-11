package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.domain.Ranking;
import com.rosewar.scoretracker.domain.Stat;
import com.rosewar.scoretracker.repository.RankingRepository;
import com.rosewar.scoretracker.repository.StatRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;
    private final StatRepository statRepository;

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

    @Transactional
    public void updateRanking() {
        // 모든 Stat 데이터를 가져와서 정렬
        List<Stat> allStats = statRepository.findAll(Sort.by(Sort.Direction.DESC, "winCount"));

        int rank = 1;
        for (Stat stat : allStats) {
            // 기존 Ranking을 가져오거나 없으면 새로 생성
            Ranking ranking = rankingRepository.findById(stat.getUserId())
                    .orElse(Ranking.builder()
                            .userId(stat.getUserId())
                            .stat(stat)
                            .build());

            // Ranking 업데이트
            ranking.setRanking(rank++);
            ranking.setUpdatedAt(LocalDateTime.now());

            rankingRepository.save(ranking); // 랭킹 저장
        }
    }
}

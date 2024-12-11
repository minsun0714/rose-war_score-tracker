package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, String> {
    List<Ranking> findAllByOrderByStatWinCountDesc();
}
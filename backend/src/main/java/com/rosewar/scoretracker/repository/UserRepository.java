package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Player, String> {
    // 페이징을 사용하여 유저 리스트 조회
    @NonNull
    Page<Player> findAll(Pageable pageable);

    Optional<Player> findByName(String name);
}

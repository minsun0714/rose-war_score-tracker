package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Stat;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<Stat, String> {
    // 페이징을 사용하여 유저 리스트 조회
    @NonNull
    Page<Stat> findAll(Pageable pageable);

    // Pessimistic Lock 적용 예시
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Stat s WHERE s.userId = :userId")
    Optional<Stat> findByIdWithLock(@Param("userId") String userId);
}

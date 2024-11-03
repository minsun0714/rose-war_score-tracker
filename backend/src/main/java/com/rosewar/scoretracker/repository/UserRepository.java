package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 페이징을 사용하여 유저 리스트 조회
    @NonNull
    Page<User> findAll(Pageable pageable);
}

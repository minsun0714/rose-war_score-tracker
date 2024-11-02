package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public User insertUser(User user);

    public List<User> selectUsersList();

    public Optional<User> selectUserById();

    public void updateUser(String userId, User user);

    public void deleteUser(String userId);
}

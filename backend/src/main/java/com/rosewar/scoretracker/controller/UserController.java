package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.MyInfoUpdateDTO;
import com.rosewar.scoretracker.dto.request.SignUpFormDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 생성 (회원가입)
    @PostMapping
    public ResponseEntity<UserInfoDTO> createUser(@RequestBody SignUpFormDTO signUpFormDTO) {
        UserInfoDTO createdUser = userService.createUser(signUpFormDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 특정 유저 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserById(@PathVariable String userId) {
        UserInfoDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // 유저 정보 업데이트
    @PutMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> updateUser(
            @PathVariable String userId,
            @RequestBody MyInfoUpdateDTO myInfoUpdateDTO) {
        UserInfoDTO updatedUser = userService.updateUser(userId, myInfoUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

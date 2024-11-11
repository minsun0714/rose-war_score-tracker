package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.MyInfoUpdateDTO;
import com.rosewar.scoretracker.dto.request.SignUpFormDTO;
import com.rosewar.scoretracker.dto.response.SignUpResponseDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.security.JwtTokenProvider;
import com.rosewar.scoretracker.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {

        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 유저 생성 (회원가입)
    @PostMapping
    public ResponseEntity<SignUpResponseDTO> createUser(@Valid @RequestBody SignUpFormDTO signUpFormDTO, HttpServletResponse response) {
        SignUpResponseDTO createdUser = userService.createUser(signUpFormDTO, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 특정 유저 조회
    @GetMapping
    public ResponseEntity<UserInfoDTO> getUserById() {
        UserInfoDTO user = userService.getUserById();
        return ResponseEntity.ok(user);
    }

    // 유저 정보 업데이트
    @PutMapping
    public ResponseEntity<UserInfoDTO> updateUser(
            @Valid
            @RequestBody MyInfoUpdateDTO myInfoUpdateDTO) {
        UserInfoDTO updatedUser = userService.updateUser(myInfoUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // 유저 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}

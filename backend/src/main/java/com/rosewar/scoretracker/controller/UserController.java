package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.MyInfoUpdateDTO;
import com.rosewar.scoretracker.dto.request.SignUpFormDTO;
import com.rosewar.scoretracker.dto.response.SignUpResponseDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
            @ModelAttribute MyInfoUpdateDTO myInfoUpdateDTO,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            UserInfoDTO updatedUser = userService.updateUser(myInfoUpdateDTO, file);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            // 예외 로그 출력
            e.printStackTrace();
            // 적절한 HTTP 상태 코드와 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    // 유저 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}

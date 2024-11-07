package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.SignInFormDTO;
import com.rosewar.scoretracker.dto.response.JwtToken;
import com.rosewar.scoretracker.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody SignInFormDTO loginRequest) {
        System.out.println(loginRequest + " " + authenticationManager);
        // 유저 인증 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword())
        );
        System.out.println(authentication);
        // 인증에 성공하면 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtToken> refresh(@RequestBody JwtToken jwtTokenRequest) {
        // Refresh Token을 검증하고 새로운 Access Token 발급
        if (jwtTokenProvider.validateToken(jwtTokenRequest.getRefreshToken())) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenRequest.getRefreshToken());
            JwtToken newToken = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(newToken);
        } else {
            return ResponseEntity.status(403).body(null); // 유효하지 않은 Refresh Token
        }
    }
}

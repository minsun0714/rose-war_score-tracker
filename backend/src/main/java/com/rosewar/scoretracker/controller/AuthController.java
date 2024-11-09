package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.SignInFormDTO;
import com.rosewar.scoretracker.dto.response.JwtTokenDTO;
import com.rosewar.scoretracker.security.AuthService;
import com.rosewar.scoretracker.security.JwtToken;
import com.rosewar.scoretracker.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> login(@RequestBody SignInFormDTO loginRequest, HttpServletResponse response) {
        // 유저 인증 처리
        // 인증에 성공하면 토큰 생성
        JwtToken jwtToken = authService.authenticateAndGenerateToken(loginRequest.getUserId(), loginRequest.getPassword());

        setRefreshTokenCookie(response, jwtToken.getRefreshToken());

        return ResponseEntity.ok(new JwtTokenDTO("Bearer", jwtToken.getAccessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDTO> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.getRefreshTokenFromCookie(request);
        // Refresh Token을 검증하고 새로운 Access Token 발급
        if (jwtTokenProvider.validateToken(refreshToken)) {
            JwtToken newToken = authService.refreshAccessToken(refreshToken);

            setRefreshTokenCookie(response, newToken.getRefreshToken());

            return ResponseEntity.ok(new JwtTokenDTO("Bearer", newToken.getAccessToken()));
        } else {
            return ResponseEntity.status(403).body(null); // 유효하지 않은 Refresh Token
        }
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS에서만 전송되도록 설정
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(24 * 60 * 60); // 24시간
        response.addCookie(refreshTokenCookie);

        // SameSite 설정 추가 (직접 헤더로 추가)
        response.setHeader("Set-Cookie", "refreshToken=" + refreshToken + "; HttpOnly; Secure; Path=/; Max-Age=" + (24 * 60 * 60) + "; SameSite=Lax");
    }
}

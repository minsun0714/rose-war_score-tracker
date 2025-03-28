package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.request.MyInfoUpdateDTO;
import com.rosewar.scoretracker.dto.request.SignUpFormDTO;
import com.rosewar.scoretracker.dto.response.SignUpResponseDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.event.UserCreatedEvent;
import com.rosewar.scoretracker.exception.UserNotAuthenticatedException;
import com.rosewar.scoretracker.repository.UserRepository;
import com.rosewar.scoretracker.security.AuthService;
import com.rosewar.scoretracker.security.JwtToken;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.multipart.MultipartFile;

import static com.rosewar.scoretracker.util.CookieUtils.setRefreshTokenCookie;
import static com.rosewar.scoretracker.util.DTOMapper.toSignUpResponseDTO;
import static com.rosewar.scoretracker.util.DTOMapper.toUserInfoDTO;

@Service
@Transactional
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Autowired
    public UserService(AuthService authService, UserRepository userRepository, ApplicationEventPublisher eventPublisher, PasswordEncoder passwordEncoder, S3Service s3Service) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
    }

    // 사용자 생성
    @Transactional
    public SignUpResponseDTO createUser(SignUpFormDTO userRequestDTO, HttpServletResponse response) {
        Player player = Player.builder()
                .userId(userRequestDTO.getUserId())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))  // 보안상 해시 처리 필요
                .name(userRequestDTO.getName())
                .nickname(userRequestDTO.getNickname())
                .build();

        Player savedPlayer = userRepository.save(player);

        eventPublisher.publishEvent(new UserCreatedEvent(savedPlayer.getUserId()));

        JwtToken jwtToken = authService.authenticateAndGenerateToken(userRequestDTO.getUserId(), userRequestDTO.getPassword());

        setRefreshTokenCookie(response, jwtToken.getRefreshToken());

        return toSignUpResponseDTO(savedPlayer, jwtToken.getAccessToken());
    }


    // 사용자 조회
    public UserInfoDTO getUserById() {
        String userId = getAuthenticatedUserId();
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toUserInfoDTO(player);
    }

    // 사용자 정보 업데이트
    @Transactional
    public UserInfoDTO updateUser(MyInfoUpdateDTO userRequestDTO, MultipartFile file) {
        String userId = getAuthenticatedUserId();
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRequestDTO.getPassword() != null) {
            validatePasswordMatch(userRequestDTO.getPassword(), userRequestDTO.getConfirmPassword());
            player.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        String fileUrl = s3Service.uploadFile(file, userId);

        player.setProfileImg(fileUrl);
        Player updatedPlayer = userRepository.save(player);
        return toUserInfoDTO(updatedPlayer);
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser() {
        String userId = getAuthenticatedUserId();
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }

    // 비밀번호 일치 여부를 확인하는 메서드
    private void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }

    public String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof String)) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }
        return (String) authentication.getPrincipal();
    }
}

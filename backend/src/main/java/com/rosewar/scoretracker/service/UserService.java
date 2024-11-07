package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.request.MyInfoUpdateDTO;
import com.rosewar.scoretracker.dto.request.SignUpFormDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.rosewar.scoretracker.util.DTOMapper.toUserInfoDTO;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final StatService statService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, StatService statService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.statService = statService;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 생성
    @Transactional
    public UserInfoDTO createUser(SignUpFormDTO userRequestDTO) {
        Player player = Player.builder()
                .userId(userRequestDTO.getUserId())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))  // 보안상 해시 처리 필요
                .name(userRequestDTO.getName())
                .nickname(userRequestDTO.getNickname())
                .build();

        Player savedPlayer = userRepository.save(player);
        statService.createStat(player.getUserId()); // 결합도 낮출 필요
        return toUserInfoDTO(savedPlayer);
    }

    // 사용자 조회
    public UserInfoDTO getUserById(String userId) {
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toUserInfoDTO(player);
    }

    // 사용자 정보 업데이트
    @Transactional
    public UserInfoDTO updateUser(String userId, MyInfoUpdateDTO userRequestDTO) {
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRequestDTO.getPassword() != null) {
            validatePasswordMatch(userRequestDTO.getPassword(), userRequestDTO.getConfirmPassword());
            player.setPassword(userRequestDTO.getPassword()); // 보안상 해시 처리 필요
        }

        Player.builder()
                .profileImg(userRequestDTO.getProfileImg())
                .nickname(userRequestDTO.getNickname());

        Player updatedPlayer = userRepository.save(player);
        return toUserInfoDTO(updatedPlayer);
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(String userId) {
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
}

package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.request.MyInfoUpdateDTO;
import com.rosewar.scoretracker.dto.request.SignUpFormDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rosewar.scoretracker.util.DTOMapper.toUserInfoDTO;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final StatService statService;

    @Autowired
    public UserService(UserRepository userRepository, StatService statService) {
        this.userRepository = userRepository;
        this.statService = statService;
    }

    // 사용자 생성
    @Transactional
    public UserInfoDTO createUser(SignUpFormDTO userRequestDTO) {
        Player player = new Player();
        player.setUserId(userRequestDTO.getUserId());
        player.setPassword(userRequestDTO.getPassword());  // 보안상 해시 처리 필요
        player.setName(userRequestDTO.getName());
        player.setNickname(userRequestDTO.getNickname());

        Player savedPlayer = userRepository.save(player);
        statService.createStat(player.getUserId());
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
        if (userRequestDTO.getProfileImg() != null) {
            player.setProfileImg(userRequestDTO.getProfileImg());
        }
        player.setPassword(userRequestDTO.getPassword());
        player.setNickname(userRequestDTO.getNickname());
        player.setProfileImg(userRequestDTO.getProfileImg());

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

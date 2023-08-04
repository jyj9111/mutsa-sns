package com.mutsa.sns.domain.user.service;

import com.mutsa.sns.domain.user.dto.UserLoginRequestDto;
import com.mutsa.sns.domain.user.dto.UserLoginResponseDto;
import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.entity.User;
import com.mutsa.sns.domain.user.exception.PasswordNotMatched;
import com.mutsa.sns.domain.user.exception.UsernameIsExist;
import com.mutsa.sns.domain.user.exception.UsernameNotExist;
import com.mutsa.sns.global.auth.JwtTokenUtils;
import com.mutsa.sns.global.common.ResponseDto;
import com.mutsa.sns.global.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final FileHandler fileHandler;

    // 회원가입
    public ResponseDto createUser(UserRegisterDto dto) {
        if (manager.userExists(dto.getUsername())) {
            log.warn("creatUser() >> {} 이미 존재하는 이름", dto.getUsername());
            throw new UsernameIsExist();
        }
        log.info(dto.toString());
        manager.createUser(CustomUserDetails.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build());
        log.info("createUser() >> {} 회원가입 완료", dto.getUsername());

        return new ResponseDto("회원가입이 완료 되었습니다.");
    }

    // 유저 검증 (로그인)
    public UserLoginResponseDto verifyUser(UserLoginRequestDto dto) {
        if (!manager.userExists(dto.getUsername())) {
            log.warn("[{}]: 등록되지 않은 유저 로그인 시도", dto.getUsername());
            throw new UsernameNotExist();
        }

        CustomUserDetails userDetails = (CustomUserDetails)manager.loadUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            log.warn("[{}]: 일치하지 않는 비밀번호 입력", userDetails.getUsername());
            throw new PasswordNotMatched();
        }

        log.info("[{}]: 등록된 유저 로그인", userDetails.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new UserLoginResponseDto(userDetails.getId(), userDetails.getUsername(), token);
    }

    // 유저 프로필 이미지 등록
    public ResponseDto updateProfileImage(String username, MultipartFile profileImg) {
        CustomUserDetails user = (CustomUserDetails)manager.loadUserByUsername(username);
        User updateUser = User.fromUserDetails(user);

        String imgUrl = fileHandler.getProfileImgPath(username, profileImg);

        manager.updateUser(CustomUserDetails.builder()
                .id(updateUser.getId())
                .username(updateUser.getUsername())
                .password(updateUser.getPassword())
                .email(updateUser.getEmail())
                .phone(updateUser.getPhone())
                .profileImg(imgUrl)
                .build());

        return new ResponseDto("프로필 이미지 등록에 성공했습니다.");
    }


}

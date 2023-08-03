package com.mutsa.sns.domain.user.service;

import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.exception.UsernameIsExist;
import com.mutsa.sns.domain.user.repo.UserRepository;
import com.mutsa.sns.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    public ResponseDto createUser(UserRegisterDto dto) {
        if (manager.userExists(dto.getUsername())) {
            log.warn("creatUser() >> {} 이미 존재하는 이름", dto.getUsername());
            throw new UsernameIsExist();
        }

        manager.createUser(CustomUserDetails.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build());
        log.info("createUser() >> {} 회원가입 완료", dto.getUsername());

        return new ResponseDto("회원가입이 완료 되었습니다.");
    }
}

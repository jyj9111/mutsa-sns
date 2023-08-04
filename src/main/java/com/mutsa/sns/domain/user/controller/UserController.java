package com.mutsa.sns.domain.user.controller;

import com.mutsa.sns.domain.user.dto.UserLoginRequestDto;
import com.mutsa.sns.domain.user.dto.UserLoginResponseDto;
import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import com.mutsa.sns.domain.user.exception.PasswordDuplicateCheckFail;
import com.mutsa.sns.domain.user.service.UserService;
import com.mutsa.sns.global.common.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseDto register(@Valid @RequestBody UserRegisterDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            log.warn("{} 비밀번호 중복확인 실패", dto.getUsername());
            throw new PasswordDuplicateCheckFail();
        }
        return userService.createUser(dto);
    }

    // 로그인
    @PostMapping("/login")
    public UserLoginResponseDto login(
           @Valid @RequestBody UserLoginRequestDto dto
    ) {
        return userService.verifyUser(dto);
    }

    // 프로필 이미지 등록
    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto updateProfileImage(
            Principal principal,
            @RequestParam("image") MultipartFile profileImg
    ) {
        return userService.updateProfileImage(principal.getName(), profileImg);
    }
}

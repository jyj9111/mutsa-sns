package com.mutsa.sns.domain.user.controller;

import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import com.mutsa.sns.domain.user.exception.PasswordDuplicateCheckFail;
import com.mutsa.sns.domain.user.service.UserService;
import com.mutsa.sns.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseDto register(@RequestBody UserRegisterDto dto) {
        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            log.warn("{} 비밀번호 중복확인 실패", dto.getUsername());
            throw new PasswordDuplicateCheckFail();
        }
        return userService.createUser(dto);
    }
}

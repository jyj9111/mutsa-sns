package com.mutsa.sns.domain.user.dto;

import lombok.Data;

@Data
public class UserLoginResponseDto {
    private Long id;
    private String username;
    private String token;

    public UserLoginResponseDto(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}

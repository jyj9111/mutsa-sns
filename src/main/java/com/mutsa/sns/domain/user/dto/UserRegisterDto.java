package com.mutsa.sns.domain.user.dto;

import com.mutsa.sns.domain.user.entity.User;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String passwordCheck;
    private String email;
    private String phone;


}

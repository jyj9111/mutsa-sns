package com.mutsa.sns.domain.user.entity;

import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String password;

    private String email;
    private String phone;
    private String profileImg;

    public static User fromRegisterDto(UserRegisterDto dto) {
        User userEntity = new User();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(dto.getPassword());
        userEntity.setEmail(dto.getEmail());
        userEntity.setPhone(dto.getPhone());

        return userEntity;
    }
}

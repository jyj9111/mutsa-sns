package com.mutsa.sns.domain.user.entity;

import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
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
        User user = new User();
        user.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phone(dto.getPhone());
        return user;
    }
}

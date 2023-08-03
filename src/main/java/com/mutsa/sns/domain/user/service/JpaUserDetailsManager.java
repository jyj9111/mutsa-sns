package com.mutsa.sns.domain.user.service;

import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.entity.User;
import com.mutsa.sns.domain.user.exception.UsernameIsExist;
import com.mutsa.sns.domain.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.warn("{} 존재하지 않는 이름", username);
            throw new UsernameNotFoundException(username);
        }
        return CustomUserDetails.fromEntity(optionalUser.get());
    }

    @Override
    public void createUser(UserDetails user) {
        if (this.userExists(user.getUsername())) {
            log.warn("{} 이미 존재하는 이름", user.getUsername());
            throw new UsernameIsExist();
        }
        try {
            userRepository.save(((CustomUserDetails)user).newEntity());
            log.info("{} 유저등록 성공", user.getUsername());
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }
}

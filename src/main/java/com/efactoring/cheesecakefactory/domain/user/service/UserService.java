package com.efactoring.cheesecakefactory.domain.user.service;

import com.efactoring.cheesecakefactory.domain.user.dto.SignupRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupResponseDto;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import com.efactoring.cheesecakefactory.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 회원가입
    public SignupResponseDto registerUser(SignupRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(dto.getStatus());
        user.setRole(dto.getRole());

        userRepository.save(user);

        return new SignupResponseDto(user);
    }
}

package com.efactoring.cheesecakefactory.domain.user.service;

import com.efactoring.cheesecakefactory.domain.user.dto.PatchUserRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.UserInfoResponseDto;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import com.efactoring.cheesecakefactory.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus("1");
        user.setRole(dto.getRole());
        user.setAddress(dto.getAddress());

        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    // 유저 로그인
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    // 유저 정보수정
    public User patchUser(Long id, PatchUserRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는아이디"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) { // 앞에가 평문비밀번호, 뒤에가 암호화비밀번호
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);

        return user;
    }

    public UserInfoResponseDto getUserInfo(Long id, HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당아이디없음"));
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalArgumentException("로그인이 안되어있습니다.");
        }
        User user2 = (User) session.getAttribute("user");

        if (!user.getEmail().equals(user2.getEmail())) {
            throw new IllegalArgumentException("본인정보만 조회가능");
        }

        return new UserInfoResponseDto(user);
    }
}

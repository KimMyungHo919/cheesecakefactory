package com.efactoring.cheesecakefactory.domain.user.service;

import com.efactoring.cheesecakefactory.domain.user.dto.*;
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

        if (!dto.getRole().equals("USER") && !dto.getRole().equals("OWNER")) {
            throw new IllegalArgumentException("유저의 ROLE은 USER 혹은 OWNER만 가능합니다.");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus("Active");
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
    public User patchUser(Long id, PatchUserRequestDto dto, HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는아이디"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalArgumentException("로그인이 안되어있습니다.");
        }
        User loginUser = (User) session.getAttribute("user");

        if (!user.getEmail().equals(loginUser.getEmail())) {
            throw new IllegalArgumentException("본인정보만 수정가능");
        }

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) { // 앞에가 평문비밀번호, 뒤에가 암호화비밀번호
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);

        return user;
    }

    // 유저 정보조회. 자신만가능.
    public UserInfoResponseDto getUserInfo(Long id, HttpServletRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당아이디없음"));
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalArgumentException("로그인이 안되어있습니다.");
        }
        User loginUser = (User) session.getAttribute("user");

        if (!user.getEmail().equals(loginUser.getEmail())) {
            throw new IllegalArgumentException("본인정보만 조회가능");
        }

        return new UserInfoResponseDto(user);
    }

    // 유저탈퇴
    public void userStatusChange(Long id, HttpServletRequest request, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일치하는 유저없음"));

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalArgumentException("로그아웃상태입니다.");
        }
        User loginUser = (User) session.getAttribute("user");

        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        if (!user.getEmail().equals(loginUser.getEmail())) {
            throw new IllegalArgumentException("본인만 탈퇴가능합니다.");
        }

        if ("Deleted".equals(user.getStatus())) {
            throw new IllegalArgumentException("이미 탈퇴상태인 유저입니다.");
        }

        user.setStatus("Deleted");
        userRepository.save(user);
    }
}

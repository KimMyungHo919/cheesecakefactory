package com.efactoring.cheesecakefactory.domain.user.controller;

import com.efactoring.cheesecakefactory.domain.user.dto.*;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import com.efactoring.cheesecakefactory.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public SignupResponseDto registerUser(@RequestBody @Valid SignupRequestDto dto) {
        return userService.registerUser(dto);
    }

    // 로그인
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            throw new IllegalArgumentException("이미 로그인된 사용자입니다.");
        }

        User user = userService.loginUser(dto.getEmail(), dto.getPassword());
        session = request.getSession(true);
        session.setAttribute("user", user);
        return new LoginResponseDto(user);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalArgumentException("이미 로그아웃상태");
        }
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저정보수정
    @PatchMapping("/{id}")
    public PatchUserResponseDto patchUser(@PathVariable Long id, @RequestBody @Valid PatchUserRequestDto dto, HttpServletRequest request) {
        User user = userService.patchUser(id, dto, request);
        return new PatchUserResponseDto(user);
    }

    // id 정보조회
    @GetMapping("/{id}")
    public UserInfoResponseDto getUserInfo(@PathVariable Long id, HttpServletRequest request) {
        return userService.getUserInfo(id, request);
    }

    // 회원탈퇴
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> userDelete(@PathVariable Long id, HttpServletRequest request, @RequestBody deleteUserRequestDto dto) {
        userService.userStatusChange(id, request, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

package com.efactoring.cheesecakefactory.domain.user.controller;

import com.efactoring.cheesecakefactory.domain.user.dto.*;
import com.efactoring.cheesecakefactory.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> registerUser(@RequestBody @Valid SignupRequestDto dto, HttpServletRequest request) {
        SignupResponseDto signupUser = userService.registerUser(dto, request);

        return new ResponseEntity<>(signupUser, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto, HttpServletRequest request) {
        LoginResponseDto loginUser = userService.loginUser(dto.getEmail(), dto.getPassword(), request);

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그아웃된 사용자입니다.");
        }
        session.invalidate();

        return new ResponseEntity<>("로그아웃 성공!",HttpStatus.OK);
    }

    // 유저정보수정
    @PatchMapping("/{id}")
    public ResponseEntity<PatchUserResponseDto> patchUser(@PathVariable Long id, @RequestBody @Valid PatchUserRequestDto dto, HttpServletRequest request) {
        PatchUserResponseDto patchUser = userService.patchUser(id, dto, request);

        return new ResponseEntity<>(patchUser, HttpStatus.OK);
    }

    // id 정보조회
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@PathVariable Long id, HttpServletRequest request) {
        UserInfoResponseDto userInfo = userService.getUserInfo(id, request);

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    // 회원탈퇴
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> userDelete(@PathVariable Long id, HttpServletRequest request, @RequestBody DeleteUserRequestDto dto) {
        userService.userStatusChange(id, request, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

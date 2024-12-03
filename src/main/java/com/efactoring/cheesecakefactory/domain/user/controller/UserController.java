package com.efactoring.cheesecakefactory.domain.user.controller;

import com.efactoring.cheesecakefactory.domain.user.dto.*;
import com.efactoring.cheesecakefactory.domain.user.service.UserService;
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
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        return null;
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저정보수정
    @PatchMapping("/{id}")
    public PatchUserResponseDto patchUser(@PathVariable Long id, @RequestBody PatchUserRequestDto dto) {
        return null;
    }

    // 본인정보조회
    @GetMapping("/{id}")
    public String usernamejo(@PathVariable Long id) {
        return null;
    }

    // 회원탈퇴
    @PatchMapping("/{id}/delete")
    public String userDelete(@PathVariable Long id) {
        return null;
    }



}

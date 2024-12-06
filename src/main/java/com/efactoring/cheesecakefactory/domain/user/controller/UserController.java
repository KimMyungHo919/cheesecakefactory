package com.efactoring.cheesecakefactory.domain.user.controller;

import com.efactoring.cheesecakefactory.domain.base.SuccessResponseDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.DeleteUserRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.LoginRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.LoginResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.PatchUserRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.PatchUserResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.UserInfoResponseDto;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import com.efactoring.cheesecakefactory.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

        return new ResponseEntity<>("로그아웃 성공!", HttpStatus.OK);
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

    /**
     * 유저별 주문 조회
     *
     * @param id user entity id
     * @return List<OrderResponseDto>
     * @throws ResponseStatusException forbidden = 본인 주문 아닌 경우 || user 가 아닌경우
     */
    @GetMapping("/{id}/orders")
    public ResponseEntity<SuccessResponseDto> getUserOrders(
            @PathVariable Long id,
            @SessionAttribute User user
    ) {
        List<OrderResponseDto> orderResponseDtoList = userService.getUserOrder(id, user);

        SuccessResponseDto successResponseDto = new SuccessResponseDto("OK", 200, orderResponseDtoList);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}

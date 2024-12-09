package com.efactoring.cheesecakefactory.domain.user.controller;

import com.efactoring.cheesecakefactory.domain.base.SuccessResponseDto;
import com.efactoring.cheesecakefactory.domain.model.ReturnStatusCode;
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
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     *
     * @param dto
     * @param request
     * @return SignupResponseDto
     */
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponseDto> registerUser(
            @RequestBody @Valid SignupRequestDto dto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그아웃 먼저 해주세요.");
        }

        SignupResponseDto signupUser = userService.registerUser(dto);

        ReturnStatusCode status = ReturnStatusCode.CREATED;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), signupUser);

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }

    /**
     * 로그인
     *
     * @param dto
     * @param request
     * @return LoginResponseDto
     */
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDto> login(
            @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    ) {
        User user = userService.loginUser(dto.getEmail(), dto.getPassword());

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 로그인된 사용자입니다.");
        }

        session = request.getSession(true);
        session.setAttribute("user", user);

        LoginResponseDto loginUser = new LoginResponseDto(user);

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), loginUser);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    /**
     * 로그아웃
     *
     * @param request
     * @return String
     */
    @PostMapping("/logout")
    public ResponseEntity<SuccessResponseDto> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), "로그아웃 성공!");

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    /**
     * 유저정보수정 (name,password)
     *
     * @param id
     * @param dto
     * @param user
     * @return PatchUserResponseDto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> patchUser(
            @PathVariable Long id,
            @RequestBody @Valid PatchUserRequestDto dto,
            @SessionAttribute(name = "user") User user
    ) {
        if (!Objects.equals(id, user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신의 정보만 수정가능합니다. 아이디를 확인해주세요.");
        }

        PatchUserResponseDto patchUser = userService.patchUser(id, dto);

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), patchUser);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    /**
     * 유저id로 정보조회
     *
     * @param id
     * @param user
     * @return UserInfoResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> getUserInfo(
            @PathVariable Long id,
            @SessionAttribute(name = "user") User user
    ) {
        if (!Objects.equals(user.getId(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신의 정보만 조회가능합니다. 아이디를 확인해주세요.");
        }

        UserInfoResponseDto userInfo = userService.getUserInfo(id);

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), userInfo);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    /**
     * 유저탈퇴. 상태변경으로 처리
     *
     * @param id
     * @param dto
     * @param request
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<SuccessResponseDto> userDelete(
            @PathVariable Long id,
            @RequestBody DeleteUserRequestDto dto,
            HttpServletRequest request
    ) {
        userService.userStatusChange(id, dto.getPassword());

        HttpSession session = request.getSession(false);
        session.invalidate();

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), null);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
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

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), orderResponseDtoList);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}

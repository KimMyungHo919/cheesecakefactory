package com.efactoring.cheesecakefactory.domain.user.service;

import com.efactoring.cheesecakefactory.domain.model.UserRole;
import com.efactoring.cheesecakefactory.domain.model.UserStatus;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import com.efactoring.cheesecakefactory.domain.order.repository.OrderRepository;
import com.efactoring.cheesecakefactory.domain.user.config.PasswordEncoder;
import com.efactoring.cheesecakefactory.domain.user.dto.PatchUserRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.PatchUserResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupRequestDto;
import com.efactoring.cheesecakefactory.domain.user.dto.SignupResponseDto;
import com.efactoring.cheesecakefactory.domain.user.dto.UserInfoResponseDto;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import com.efactoring.cheesecakefactory.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    // 유저 회원가입
    public SignupResponseDto registerUser(SignupRequestDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(dto.getRole());
        user.setAddress(dto.getAddress());

        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    // 유저 로그인
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        if (Objects.equals(user.getStatus(), UserStatus.DELETED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "탈퇴한 회원입니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호를 다시 확인해주세요.");
        }

        return user;
    }

    // 유저 정보수정
    public PatchUserResponseDto patchUser(Long id, PatchUserRequestDto dto) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는아이디"));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) { // 앞에가 평문비밀번호, 뒤에가 암호화비밀번호
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호를 다시 확인해주세요.");
        }

        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) { // 앞에가 평문비밀번호, 뒤에가 암호화비밀번호
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 비밀번호와 수정할 비밀번호가 같습니다.");
        }

        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);

        return new PatchUserResponseDto(user);
    }

    // 유저 정보조회. 자신만가능.
    public UserInfoResponseDto getUserInfo(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당아이디없음"));

        return new UserInfoResponseDto(user);
    }

    // 유저탈퇴
    public void userStatusChange(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일치하는 유저없음"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호를 다시 확인해주세요.");
        }

        if (Objects.equals(user.getStatus(), UserStatus.DELETED)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 탈퇴한 유저입니다.");
        }

        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    public List<OrderResponseDto> getUserOrder(Long id, User user) {
        if (!Objects.equals(id, user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 주문만 조회할 수 있습니다.");
        } else if (!Objects.equals(user.getRole(), UserRole.USER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user 유저만 조회할 수 있습니다.");
        }

        List<Orders> orders = orderRepository.findByUserId(user.getId());
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Orders order : orders) {
            orderResponseDtoList.add(new OrderResponseDto(order));
        }

        return orderResponseDtoList;

    }
}

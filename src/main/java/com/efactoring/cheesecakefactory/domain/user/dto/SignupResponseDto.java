package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {

    private Long id;

    private String email;

    private String status;

    private String role;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.status = user.getStatus();
    }
}

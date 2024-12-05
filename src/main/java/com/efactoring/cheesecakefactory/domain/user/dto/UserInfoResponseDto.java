package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoResponseDto {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String status;
    private String role;

    public UserInfoResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.status = user.getStatus();
    }
}

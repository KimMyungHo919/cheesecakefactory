package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserInfoResponseDto {

    private Long id;

    private String email;

    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public UserInfoResponseDto(User user) {
        this.id = user.getId();;
        this.email = user.getEmail();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}

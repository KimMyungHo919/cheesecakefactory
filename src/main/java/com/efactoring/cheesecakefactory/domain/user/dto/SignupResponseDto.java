package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SignupResponseDto extends BaseEntity {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String status;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}

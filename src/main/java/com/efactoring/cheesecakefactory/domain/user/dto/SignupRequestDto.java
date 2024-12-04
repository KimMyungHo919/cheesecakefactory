package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto extends BaseEntity {

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 15) // 대소문자포함 영문+숫자+특수문자를 최소 1글자씩 포함해야함
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String name;

    @NotBlank
    private String role;

}

package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto extends BaseEntity {

    @NotBlank
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 최소 1글자 이상 포함하고 8자 이상이어야 합니다."
    )
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String name;

    @NotBlank
    private String role;

}

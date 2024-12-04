package com.efactoring.cheesecakefactory.domain.user.dto;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchUserRequestDto extends BaseEntity {

    private String name;

    private String oldPassword;

    private String newPassword;

}

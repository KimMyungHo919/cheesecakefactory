package com.efactoring.cheesecakefactory.domain.user.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.model.UserRole;
import com.efactoring.cheesecakefactory.domain.model.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "address")
    private String address;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "status")
    private UserStatus status;

    public User() {
    }

    public User(String name, String email, String password, String address, UserRole role, UserStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public void deleteUser() {
        if (Objects.equals(this.status, UserStatus.DELETED)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 탈퇴한 유저입니다.");
        }
        this.status = UserStatus.DELETED;
    }

    public void patchUser(String changeName, String newPassword) {
        this.name = changeName;
        this.password = newPassword;
    }
}

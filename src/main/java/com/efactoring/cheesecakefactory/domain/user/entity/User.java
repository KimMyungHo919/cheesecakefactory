package com.efactoring.cheesecakefactory.domain.user.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @NotBlank
    @Column(name = "role")
    private String role;

    @NotBlank
    @Column(name = "status")
    private String status;

    public User() { }

    public User(String name, String email, String password, String address, String role, String status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.status = status;
    }

}

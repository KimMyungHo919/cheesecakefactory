package com.efactoring.cheesecakefactory.domain.menu.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private long price;
    private String status;

//    @ManyToOne
//    private Store store;

    public Menu(String name, long price, String status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public Menu() {
    }
}

package com.efactoring.cheesecakefactory.domain.menu.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Long price;
    private String status;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(String name, Long price, String status, Store store) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.isActive = true;
        this.store = store;
    }

    public Menu() {
    }

    public void updateMenu(String name, Long price, String status) {
        this.name = name == null ? this.name : name;
        this.price = price == null || price == 0 ? this.price : price;
        this.status = status == null ? this.status : status;
    }

    public void deleteMenu() {
        this.isActive = false;
    }
}

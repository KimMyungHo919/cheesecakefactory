package com.efactoring.cheesecakefactory.domain.order.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;
    private long totalPrice;
    private String status;

    @ManyToOne
    private Menu menu;

//    @ManyToOne
//    private Store store;

    public Orders(int quantity, long totalPrice, String status) {
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Orders() {
    }
}

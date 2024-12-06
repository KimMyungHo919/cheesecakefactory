package com.efactoring.cheesecakefactory.domain.review.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

    @Column(nullable = false)
    private int rating;

    public Review(Orders orders, int rating, String content) {
        this.orders = orders;
        this.rating = rating;
        this.content = content;
    }

    public Long getMenuId() {
        return this.orders.getMenu().getId();
    }

    public Long getStoreId() {
        return this.orders.getStore().getId();
    }

    public Long getUserId() {
        return this.orders.getUser().getId();
    }

}

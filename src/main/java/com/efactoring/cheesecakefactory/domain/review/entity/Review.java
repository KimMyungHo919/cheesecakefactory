package com.efactoring.cheesecakefactory.domain.review.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="review")
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menu_id", nullable=false)
    private Menu menu;

    @Column(nullable = false, columnDefinition = "longtext")
    private String content;

    @Column(nullable=false)
    private int rating;

    public Review(Orders orders, Menu menu, int rating, String content) {
        this.orders = orders;
        this.menu = menu;
        this.rating = rating;
        this.content = content;
    }
}

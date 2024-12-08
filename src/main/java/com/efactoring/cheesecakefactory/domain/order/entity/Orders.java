package com.efactoring.cheesecakefactory.domain.order.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.model.OrderStatus;
import com.efactoring.cheesecakefactory.domain.model.UserRole;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Getter
@Entity
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private Long totalPrice;
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Orders(int quantity, OrderStatus status, Menu menu, User user, Store store) {
        this.quantity = quantity;
        this.status = status;
        this.menu = menu;
        this.user = user;
        this.store = store;
    }

    public Orders() {
    }

    public void updateOrder(OrderStatus status) {
        this.status = status;
    }

    public void totalPrice(int quantity) {
        this.totalPrice = quantity * menu.getPrice();
    }

    /**
     * USER 유저인지 확인
     */
    public void isStatusUser(UserRole role) {
        if (!Objects.equals(role, UserRole.USER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user 유저만 메뉴를 만들 수 있습니다.");
        }
    }

    /**
     * OWNER 유저인지 & 본인 가게의 주문인지 확인
     */
    public void isStatusOwnerAndStoresOwner(User user) {
        if (!Objects.equals(user.getRole(), UserRole.OWNER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "owner 유저만 주문 상태를 변경 할 수 있습니다.");
        } else if (!Objects.equals(store.getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 가게의 주문만 변경이 가능합니다.");
        }
    }

    /**
     * 삭제된 메뉴
     */
    public void isDeletedMenu() {
        if (!menu.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제된 메뉴입니다.");
        }
    }

    /**
     * 최소 주문 금액 체크
     */
    public void minOrderPriceCheck() {
        if (totalPrice < store.getMinOrderPrice()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문금액은 최소주문금액보다 작을 수 없습니다.");
        }
    }

    /**
     * 완료된 주문
     */
    public void alreadyCompletedOrder() {
        if (status == OrderStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 완료된 주문입니다.");
        }
    }
}

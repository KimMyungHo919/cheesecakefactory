package com.efactoring.cheesecakefactory.domain.menu.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.model.MenuStatus;
import com.efactoring.cheesecakefactory.domain.model.UserRole;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Long price;
    private MenuStatus status;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    public Menu(String name, Long price, MenuStatus status, Store store) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.isActive = true;
        this.store = store;
    }

    public Menu() {
    }

    public void updateMenu(String name, Long price, MenuStatus status) {
        this.name = name == null ? this.name : name;
        this.price = price == null || price == 0 ? this.price : price;
        this.status = status == null ? this.status : status;
    }

    public void deleteMenu() {
        this.isActive = false;
    }

    /**
     * 해당 가게 메뉴인지 확인
     */
    public void storesMenu(Long storeId) {
        if (!Objects.equals(store.getId(), storeId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 가게의 메뉴가 아닙니다.");
        }
    }

    /**
     * OWNER 유저인지 확인
     */
    public void isStatusOwner(UserRole role) {
        if (!Objects.equals(role, UserRole.OWNER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "owner 유저만 메뉴를 만들 수 있습니다.");
        }
    }

    /**
     * 본인 가게 메뉴인지 확인
     */
    public void storesOwner(Long userId) {
        if (!Objects.equals(store.getUser().getId(), userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 가게에만 메뉴를 추가 할 수 있습니다.");
        }
    }
}

package com.efactoring.cheesecakefactory.domain.store.entity;

import com.efactoring.cheesecakefactory.domain.base.BaseEntity;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.model.StoreStatus;
import com.efactoring.cheesecakefactory.domain.model.UserRole;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "이름을 입력해 주세요.")
    private String name;

    @Min(value = 1, message = "최소주문금액은 0이하일 수 없습니다.")
    private Long minOrderPrice;

    @NotNull(message = "오픈시간을 입력해 주세요.")
    private LocalTime openTime;

    @NotNull(message = "마감시간을 입력해 주세요.")
    private LocalTime closeTime;

    private StoreStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menu = new ArrayList<>();

    /**
     * OWNER 유저인지 확인
     */
    public void ownerCheck(User user) {
        if (!Objects.equals(user.getRole(), UserRole.OWNER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "owner 유저만 가능합니다.");
        }
    }

    /**
     * 가게 사장인지 확인
     */
    public void storeOwnerCheck(User user) {
        if (!Objects.equals(this.user.getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 가게가 아닙니다.");
        }
    }

    /**
     * 입력된 영업 시간 확인
     *
     * @throws ResponseStatusException 400 error 오픈시간이 마감시간보다 늦을 경우
     */
    public void businessHourCheck() {
        if (closeTime.isBefore(openTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "오픈시간은 마감시간보다 늦을 수 없습니다.");
        }
    }

    public void closeDownStore() {
        if (Objects.equals(status, StoreStatus.CLOSE)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "폐업한 가게입니다.");
        }
    }
}

package com.efactoring.cheesecakefactory.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum OrderStatus {
    ORDER("ORDER"),
    ACCESS("ACCESS"),
    COOKING("COOKING"),
    COOKED("COOKED"),
    DELIVERY("DELIVERY"),
    COMPLETED("COMPLETED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static OrderStatus fromString(String status) {
        status = status.toUpperCase();

        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.status.equals(status)) {
                return orderStatus;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상태값은 (ORDER, ACCESS, COOKING, COOKED, DELIVERY, COMPLETED) 이외의 값이 될 수 없습니다.");
    }

    /**
     * 주문 상태 업데이트 로직
     *
     * @return order -> access -> cooking -> cooked -> delivery -> completed
     * <p>
     * OrderStatus를 배열로 만들어 index를 통해 다음 주문 단계 반환
     */
    @JsonCreator
    public static OrderStatus updateOrderStatus(OrderStatus status) {
        OrderStatus[] values = OrderStatus.values();
        int index = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i].status.equals(status.status)) {
                index = i + 1;
            }
        }

        return values[index];
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}

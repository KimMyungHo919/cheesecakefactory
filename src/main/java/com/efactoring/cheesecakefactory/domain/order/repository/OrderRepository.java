package com.efactoring.cheesecakefactory.domain.order.repository;

import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    default Orders findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 주문입니다."));
    }

    List<Orders> findByUserId(Long userId);

    List<Orders> findByStoreId(Long storeId);
}

package com.efactoring.cheesecakefactory.domain.order.repository;

import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByStoreId(Long storeId);
}

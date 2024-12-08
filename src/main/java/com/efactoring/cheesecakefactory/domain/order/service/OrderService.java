package com.efactoring.cheesecakefactory.domain.order.service;

import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.menu.repository.MenuRepository;
import com.efactoring.cheesecakefactory.domain.model.OrderStatus;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderRequestDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderStatusRequestDto;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import com.efactoring.cheesecakefactory.domain.order.repository.OrderRepository;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.store.repository.StoreRepository;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {
        Menu menu = menuRepository.findByIdOrElseThrow(orderRequestDto.getMenuId());
        Store store = storeRepository.findByIdOrElseThrow(orderRequestDto.getStoreId());

        Orders orders = new Orders(orderRequestDto.getQuantity(), OrderStatus.ORDER, menu, user, store);

        orders.isStatusUser(user.getRole());
        orders.totalPrice(orderRequestDto.getQuantity());
        orders.isDeletedMenu();
        orders.minOrderPriceCheck();
        menu.storesMenu(orderRequestDto.getStoreId());

        LocalTime now = LocalTime.now();

        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게 운영 시간이 끝났습니다.");
        }

        orderRepository.save(orders);

        return new OrderResponseDto(orders);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderStatusRequestDto orderStatusRequestDto, User user) {
        Orders orders = orderRepository.findByIdOrElseThrow(id);

        orders.isStatusOwnerAndStoresOwner(user);

        if (orders.getMenu().getId() != orderStatusRequestDto.getMenuId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴와 주문 번호가 틀립니다.");
        }

        orders.alreadyCompletedOrder();
        orders.updateOrder(OrderStatus.updateOrderStatus(orders.getStatus()));

        return new OrderResponseDto(orders);
    }

    public OrderResponseDto getOrder(Long id) {
        Orders orders = orderRepository.findByIdOrElseThrow(id);

        return new OrderResponseDto(orders);
    }
}

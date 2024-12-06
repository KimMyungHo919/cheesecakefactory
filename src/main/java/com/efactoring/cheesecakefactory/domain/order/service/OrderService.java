package com.efactoring.cheesecakefactory.domain.order.service;

import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.menu.repository.MenuRepository;
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
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    // 주문 준비 순서
    private final String[] ORDER_SEQUENCE = {"order", "access", "cooking", "cooked", "delivery", "completed"};

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {
        if (!user.getRole().equals("USER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "고객만 주문이 가능합니다.");
        }

        Menu menu = menuRepository.findByIdOrElseThrow(orderRequestDto.getMenuId());
        Store store = storeRepository.findByIdOrElseThrow(orderRequestDto.getStoreId());

        Long totalPrice = orderRequestDto.getQuantity() * menu.getPrice();

        LocalTime now = LocalTime.now();
        LocalTime open = store.getOpenTime();
        LocalTime close = store.getCloseTime();

        if (!menu.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제된 메뉴입니다.");
        } else if (totalPrice < store.getMinOrderPrice()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문금액은 최소주문금액보다 작을 수 없습니다.");
        } else if (now.isBefore(open) || now.isAfter(close)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게 운영 시간이 끝났습니다.");
        }

        Orders orders = new Orders(orderRequestDto.getQuantity(), totalPrice, "order", menu, user, store);

        orderRepository.save(orders);

        return new OrderResponseDto(orders);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderStatusRequestDto orderStatusRequestDto, User user) {
        Orders orders = orderRepository.findByIdOrElseThrow(id);

        if (!Objects.equals(user.getRole(), "OWNER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "owner 유저만 주문 상태를 변경 할 수 있습니다.");
        } else if (!Objects.equals(orders.getStore().getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 가게의 주문만 변경이 가능합니다.");
        }

        if (orders.getMenu().getId() != orderStatusRequestDto.getMenuId()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴와 주문 번호가 틀립니다.");
        }

        if (Objects.equals(orders.getStatus(), "completed")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 완료된 주문입니다.");
        }

        orders.updateOrder(ORDER_SEQUENCE[Arrays.asList(ORDER_SEQUENCE).indexOf(orders.getStatus()) + 1]);

        return new OrderResponseDto(orders);
    }

    public OrderResponseDto getOrder(Long id) {
        Orders orders = orderRepository.findByIdOrElseThrow(id);

        return new OrderResponseDto(orders);
    }
}

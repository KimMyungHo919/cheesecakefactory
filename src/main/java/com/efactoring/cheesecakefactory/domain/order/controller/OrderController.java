package com.efactoring.cheesecakefactory.domain.order.controller;

import com.efactoring.cheesecakefactory.domain.base.SuccessResponseDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderRequestDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderStatusRequestDto;
import com.efactoring.cheesecakefactory.domain.order.service.OrderService;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 생성
     *
     * @param orderRequestDto menuId, quantity(int)
     * @return OrderResponseDto
     */
    @PostMapping
    public ResponseEntity<SuccessResponseDto> createOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @SessionAttribute User user
    ) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto, user);

        SuccessResponseDto successResponseDto = new SuccessResponseDto("CREATED", 201, orderResponseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }

    /**
     * 주문 상태 수정
     *
     * @param id                    order Id
     * @param orderStatusRequestDto menuId(Long), status(String)
     * @return OrderResponseDto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderStatusRequestDto orderStatusRequestDto,
            @SessionAttribute User user
    ) {
        OrderResponseDto orderResponseDto = orderService.updateOrder(id, orderStatusRequestDto, user);

        SuccessResponseDto successResponseDto = new SuccessResponseDto("OK", 200, orderResponseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    /**
     * 주문 단건 조회
     *
     * @param id order id
     * @return OrderResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> getOrder(@PathVariable Long id) {
        OrderResponseDto orderResponseDto = orderService.getOrder(id);

        SuccessResponseDto successResponseDto = new SuccessResponseDto("OK", 200, orderResponseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}

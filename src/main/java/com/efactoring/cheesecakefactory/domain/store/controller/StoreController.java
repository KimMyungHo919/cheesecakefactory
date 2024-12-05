package com.efactoring.cheesecakefactory.domain.store.controller;

import com.efactoring.cheesecakefactory.domain.common.SuccessResponseDto;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.store.dto.StoreDTO;
import com.efactoring.cheesecakefactory.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;


    @GetMapping("/stores")
    public List<StoreDTO> getStore() {
        return storeService.getStores();
    }

    @GetMapping("/stores/{id}")
    public StoreDTO getStoreById(@PathVariable long id) {
        return storeService.getStoreById(id);
    }

    @DeleteMapping("/stores/{id}")
    public void deleteStoreById(@PathVariable long id) {
        storeService.deletsStoreById(id);
    }

    @PostMapping("/stores")
    public void addStore(@RequestBody StoreDTO storeDTO) {
        storeService.addStore(storeDTO);
    }

    @PutMapping("/stores/{id}")
    public void updateStore(@PathVariable long id, @RequestBody StoreDTO storeDTO) {
        storeService.updateStore(id, storeDTO);
    }

    /**
     * 가게별 주문 조회
     *
     * @param id store entity id
     * @return List<OrderResponseDto>
     * @throws org.springframework.web.server.ResponseStatusException 404 not found 없는 가게 입니다.
     */
    @GetMapping("/stores/{id}/orders")
    public ResponseEntity<SuccessResponseDto> getStoreOrders(
            @PathVariable Long id
    ) {
        List<OrderResponseDto> orderResponseDto = storeService.getStoreOrder(id);

        SuccessResponseDto successResponseDto = new SuccessResponseDto("OK", 200, orderResponseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}

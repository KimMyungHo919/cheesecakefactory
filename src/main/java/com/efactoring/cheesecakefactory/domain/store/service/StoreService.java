package com.efactoring.cheesecakefactory.domain.store.service;

import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import com.efactoring.cheesecakefactory.domain.order.repository.OrderRepository;
import com.efactoring.cheesecakefactory.domain.store.dto.StoreDTO;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    public List<StoreDTO> getStores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreDTO> storeDTOs = stores.stream().map(StoreDTO::toDTO).toList();
        return storeDTOs;
    }

    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElse(null);
        return StoreDTO.toDTO(store);
    }

    public void deletsStoreById(Long id) {
        storeRepository.deleteById(id);
    }

    public void addStore(StoreDTO storeDTO) {
        Store store = StoreDTO.toEntity(storeDTO);
        storeRepository.save(store);
    }

    public void updateStore(long id, StoreDTO storeDTO) {
        Store store = StoreDTO.toEntity(storeDTO);
        store.setId(id); // id가 있으면 수정
        storeRepository.save(store); // 저장(수정)
    }

    // todo: user 완료 후 본인 가게의 주문인지 확인하는 로직 추가
    public List<OrderResponseDto> getStoreOrder(Long id) {
        List<Orders> orders = orderRepository.findByStoreId(id);

//        if (userId != id) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 가게의 주문만 조회할 수 있습니다.");
//        }

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Orders order : orders) {
            orderResponseDtoList.add(new OrderResponseDto(order));
        }

        return orderResponseDtoList;
    }
}
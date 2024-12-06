package com.efactoring.cheesecakefactory.domain.store.service;

import com.efactoring.cheesecakefactory.domain.menu.dto.MenuResponseDto;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.menu.repository.MenuRepository;
import com.efactoring.cheesecakefactory.domain.model.StoreStatus;
import com.efactoring.cheesecakefactory.domain.order.dto.OrderResponseDto;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import com.efactoring.cheesecakefactory.domain.order.repository.OrderRepository;
import com.efactoring.cheesecakefactory.domain.store.dto.StoreDTO;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.store.repository.StoreRepository;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public List<StoreDTO> getStores() {
        List<Store> stores = storeRepository.findByStatus(StoreStatus.ACTIVE);
        List<StoreDTO> storeDTOs = stores.stream().map(StoreDTO::toDTO).toList();
        return storeDTOs;
    }

    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findByIdOrElseThrow(id);
        store.closeDownStore();

        return StoreDTO.toDTO(store);
    }

    public void deleteStoreById(Long id) {
        storeRepository.deleteById(id);
    }

    public void addStore(StoreDTO storeDTO, User user) {
        Store store = StoreDTO.toEntity(storeDTO);
        store.setUser(user);
        store.ownerCheck(user);

        if (storeRepository.countByUserIdAndStatus(user.getId(), StoreStatus.ACTIVE) > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게는 3개 이상 생성할 수 없습니다.");
        }

        store.businessHourCheck();

        storeRepository.save(store);
    }

    // todo: 수정 후 가게 주인인지 확인하는 예외처리 추가
    public void updateStore(long id, StoreDTO storeDTO, User user) {
        Store store = StoreDTO.toEntity(storeDTO);
        store.ownerCheck(user);
        store.setId(id); // id가 있으면 수정
        store.setUser(user);

        storeRepository.save(store); // 저장(수정)
    }

    public List<OrderResponseDto> getStoreOrder(Long id, User user) {
        List<Orders> orders = orderRepository.findByStoreId(id);
        Store store = storeRepository.findByIdOrElseThrow(id);

        store.ownerCheck(user);
        store.storeOwnerCheck(user);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Orders order : orders) {
            orderResponseDtoList.add(new OrderResponseDto(order));
        }

        return orderResponseDtoList;
    }

    public List<MenuResponseDto> getStoreMenuItems(Long id) {
        List<Menu> menus = menuRepository.findByStoreIdAndIsActiveIsTrue(id);

        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();

        for (Menu menu : menus) {
            menuResponseDtoList.add(new MenuResponseDto(menu));
        }

        return menuResponseDtoList;
    }
}
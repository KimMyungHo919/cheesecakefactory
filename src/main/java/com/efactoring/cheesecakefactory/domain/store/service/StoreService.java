package com.efactoring.cheesecakefactory.domain.store.service;

import com.efactoring.cheesecakefactory.domain.menu.dto.MenuResponseDto;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.menu.repository.MenuRepository;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

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

    public void addStore(StoreDTO storeDTO, User user) {
        Store store = StoreDTO.toEntity(storeDTO);
        store.setUser(user);

        storeRepository.save(store);
    }

    public void updateStore(long id, StoreDTO storeDTO) {
        Store store = StoreDTO.toEntity(storeDTO);
        store.setId(id); // id가 있으면 수정
        storeRepository.save(store); // 저장(수정)
    }

    public List<OrderResponseDto> getStoreOrder(Long id, User user) {
        List<Orders> orders = orderRepository.findByStoreId(id);
        Store store = storeRepository.findByIdOrElseThrow(id);

        if (!Objects.equals(user.getRole(), "OWNER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "owner 유저만 조회할 수 있습니다.");
        } else if (!Objects.equals(user.getId(), store.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 가게의 주문만 조회할 수 있습니다.");
        }

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
package com.efactoring.cheesecakefactory.domain.menu.service;

import com.efactoring.cheesecakefactory.domain.menu.dto.MenuRequestDto;
import com.efactoring.cheesecakefactory.domain.menu.dto.MenuResponseDto;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.menu.repository.MenuRepository;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);
        Menu menu = new Menu(menuRequestDto.getName(), menuRequestDto.getPrice(), menuRequestDto.getStatus(), store);

        menuRepository.save(menu);

        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long storeId, Long id, MenuRequestDto menuRequestDto) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);
        Menu menu = menuRepository.findByIdOrElseThrow(id);

        if (!Objects.equals(menu.getStore().getId(), store.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 가게의 메뉴가 아닙니다.");
        }

        menu.updateMenu(menuRequestDto.getName(), menuRequestDto.getPrice(), menuRequestDto.getStatus());

        return new MenuResponseDto(menu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long id) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);
        Menu menu = menuRepository.findByIdOrElseThrow(id);

        if (!Objects.equals(menu.getStore().getId(), store.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 가게의 메뉴가 아닙니다.");
        }

        if (!menu.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제된 메뉴 입니다.");
        }

        menu.deleteMenu();
    }
}

package com.efactoring.cheesecakefactory.domain.menu.service;

import com.efactoring.cheesecakefactory.domain.menu.dto.MenuRequestDto;
import com.efactoring.cheesecakefactory.domain.menu.dto.MenuResponseDto;
import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import com.efactoring.cheesecakefactory.domain.menu.repository.MenuRepository;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.store.repository.StoreRepository;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto, User user) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        Menu menu = new Menu(menuRequestDto.getName(), menuRequestDto.getPrice(), menuRequestDto.getStatus(), store);

        menu.isStatusOwner(user.getRole());
        menu.storesOwner(user.getId());

        menuRepository.save(menu);

        return new MenuResponseDto(menu);
    }

    @Transactional
    public MenuResponseDto updateMenu(Long storeId, Long id, MenuRequestDto menuRequestDto, User user) {
        storeRepository.findByIdOrElseThrow(storeId);
        Menu menu = menuRepository.findByIdOrElseThrow(id);

        menu.isStatusOwner(user.getRole());
        menu.storesOwner(user.getId());
        menu.storesMenu(storeId);

        if (menuRequestDto.getStatus() == null && menuRequestDto.getPrice() == 0 && menuRequestDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경될 값이 없습니다.");
        }

        menu.updateMenu(menuRequestDto.getName(), menuRequestDto.getPrice(), menuRequestDto.getStatus());

        return new MenuResponseDto(menu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long id, User user) {
        storeRepository.findByIdOrElseThrow(storeId);
        Menu menu = menuRepository.findByIdOrElseThrow(id);

        menu.isStatusOwner(user.getRole());
        menu.storesOwner(user.getId());
        menu.storesMenu(storeId);

        if (!menu.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제된 메뉴 입니다.");
        }

        menu.deleteMenu();
    }
}

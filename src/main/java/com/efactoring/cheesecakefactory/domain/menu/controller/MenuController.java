package com.efactoring.cheesecakefactory.domain.menu.controller;

import com.efactoring.cheesecakefactory.domain.base.SuccessResponseDto;
import com.efactoring.cheesecakefactory.domain.menu.dto.MenuRequestDto;
import com.efactoring.cheesecakefactory.domain.menu.dto.MenuResponseDto;
import com.efactoring.cheesecakefactory.domain.menu.service.MenuService;
import com.efactoring.cheesecakefactory.domain.model.ReturnStatusCode;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/stores/{storeId}/menu-items")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    /**
     * 메뉴 추가
     *
     * @param storeId        store entity id
     * @param menuRequestDto name(string), price(Long), status(sale || sold out)
     * @return MenuResponseDto
     * @throws org.springframework.web.server.ResponseStatusException 404 not found (isActive false || 없는 가게)
     */
    @PostMapping
    public ResponseEntity<SuccessResponseDto> createMenu(
            @PathVariable Long storeId,
            @Valid @RequestBody MenuRequestDto menuRequestDto,
            @SessionAttribute User user
    ) {
        MenuResponseDto menuResponseDto = menuService.createMenu(storeId, menuRequestDto, user);

        ReturnStatusCode status = ReturnStatusCode.CREATED;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), menuResponseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }

    /**
     * 메뉴 수정
     *
     * @param storeId        store entity id
     * @param id             menu entity id
     * @param menuRequestDto name(String), price(Long), status(sale || sold out)
     * @return changed MenuResponseDto
     * @throws org.springframework.web.server.ResponseStatusException 400 설정한 가게의 메뉴가 아님
     */
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long id,
            @RequestBody MenuRequestDto menuRequestDto,
            @SessionAttribute User user
    ) {
        MenuResponseDto menuResponseDto = menuService.updateMenu(storeId, id, menuRequestDto, user);

        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), menuResponseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }

    /**
     * 메뉴 삭제
     *
     * @param storeId store entity id
     * @param id      menu entity id
     * @throws org.springframework.web.server.ResponseStatusException 404 not found 이미 삭제된 메뉴
     * @throws org.springframework.web.server.ResponseStatusException 400 설정한 가게의 메뉴가 아님
     */
    @PatchMapping("/{id}/delete")
    public ResponseEntity<SuccessResponseDto> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long id,
            @SessionAttribute User user
    ) {
        ReturnStatusCode status = ReturnStatusCode.OK;
        SuccessResponseDto successResponseDto = new SuccessResponseDto(status.name(), status.getCode(), null);

        menuService.deleteMenu(storeId, id, user);

        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }
}

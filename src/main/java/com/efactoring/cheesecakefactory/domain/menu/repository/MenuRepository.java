package com.efactoring.cheesecakefactory.domain.menu.repository;

import com.efactoring.cheesecakefactory.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    default Menu findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 메뉴 입니다."));
    }

    List<Menu> findByStoreIdAndIsActiveIsTrue(long id);
}

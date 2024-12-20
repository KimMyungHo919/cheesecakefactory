package com.efactoring.cheesecakefactory.domain.store.repository;

import com.efactoring.cheesecakefactory.domain.model.StoreStatus;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    default Store findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 가게 입니다."));
    }

    List<Store> findByStatus(StoreStatus status);

    long countByUserIdAndStatus(Long id, StoreStatus status);
}

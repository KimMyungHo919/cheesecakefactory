package com.efactoring.cheesecakefactory.domain.store.repository;

import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository <Store, Long> {
}

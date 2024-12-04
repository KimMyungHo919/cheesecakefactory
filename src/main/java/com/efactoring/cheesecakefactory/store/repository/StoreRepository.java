package com.efactoring.cheesecakefactory.store.repository;

import com.efactoring.cheesecakefactory.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository <Store, Long> {
}

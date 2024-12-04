package com.efactoring.cheesecakefactory.store.controller;

import com.efactoring.cheesecakefactory.store.dto.StoreDTO;
import com.efactoring.cheesecakefactory.store.entity.Store;
import com.efactoring.cheesecakefactory.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public  class  StoreController {
        private final StoreService storeService;


        @GetMapping("/stores")
        public List<StoreDTO> getStore() {
            return storeService.getStores();
        }

        @GetMapping("/stores/{id}")
        public StoreDTO getStoreById(@PathVariable long id) {
            return storeService.getStoreById(id);
    }
        @DeleteMapping("/stores/{id}")
        public void deleteStoreById(@PathVariable long id) {
            storeService.deletsStoreById(id);
           }

        @PostMapping("/stores")
        public void addStore (@RequestBody StoreDTO storeDTO){
            storeService.addStore(storeDTO);
        }
        @PutMapping("/stores/{id}")
        public void updateStore(@PathVariable long id, @RequestBody StoreDTO storeDTO) {
            storeService.updateStore(id, storeDTO);
    }
}

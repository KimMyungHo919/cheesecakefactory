package com.efactoring.cheesecakefactory.store.service;

import com.efactoring.cheesecakefactory.store.dto.StoreDTO;
import com.efactoring.cheesecakefactory.store.entity.Store;
import com.efactoring.cheesecakefactory.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class StoreService {
    private  final StoreRepository storeRepository;

    public List<StoreDTO> getStores() {
        List<Store> stores = storeRepository.findAll();
        List<StoreDTO> storeDTOs = stores.stream().map(StoreDTO::toDTO).toList();
        return storeDTOs;
    }

    public StoreDTO getStoreById (Long id){
        Store store = storeRepository.findById(id).orElse(null);
        return StoreDTO.toDTO(store) ;
    }

    public void deletsStoreById(Long id) {
          storeRepository.deleteById(id);
    }

    public void addStore(StoreDTO storeDTO) {
        Store store = StoreDTO.toEntity(storeDTO);
        storeRepository.save(store);
    }

    public void updateStore(long id, StoreDTO storeDTO) {
        Store store = StoreDTO.toEntity(storeDTO);
        store.setId(id); // id가 있으면 수정
        storeRepository.save(store); // 저장(수정)
    }
}
package com.efactoring.cheesecakefactory.domain.store.dto;

import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class StoreDTO {
    private Long id;
    private Long userId;
    private String name;
    private Long minOrderPrice;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Entity to DTO 도움함수
    public static StoreDTO toDTO(Store store) {
        return StoreDTO.builder()
                .id(store.getId())
                .userId(store.getUser().getId())
                .name(store.getName())
                .minOrderPrice(store.getMinOrderPrice())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .status(store.getStatus())
                .createdAt(store.getCreatedAt())
                .modifiedAt(store.getModifiedAt())
                .build();
    }


    // DTO to Entity 도움함수
    public static Store toEntity(StoreDTO dto) {
        return Store.builder()
                .id(dto.getId())
                .name(dto.getName())
                .minOrderPrice(dto.getMinOrderPrice())
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .status(dto.getStatus())
                .build();
    }
}

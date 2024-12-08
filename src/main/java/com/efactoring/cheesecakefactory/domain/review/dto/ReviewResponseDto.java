package com.efactoring.cheesecakefactory.domain.review.dto;

import com.efactoring.cheesecakefactory.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private Long orderId;
    private Long menuId;
    private Long userId;
    private Long storeId;
    private int rating;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static ReviewResponseDto toDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getOrders().getId(),
                review.getMenuId(),
                review.getStoreId(),
                review.getUserId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getModifiedAt()
        );
    }
}

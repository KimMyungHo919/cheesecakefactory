package com.efactoring.cheesecakefactory.domain.review.repository;

import com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto;
import com.efactoring.cheesecakefactory.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "WHERE r.orders.store.id = :storeId " +
            "AND (:minRating IS NULL OR r.rating >= :minRating) " +
            "AND (:maxRating IS NULL OR r.rating <= :maxRating) " +
            "ORDER BY r.createdAt DESC")
    List<ReviewResponseDto> findAllByStoreIdWithFilters(
            @Param("storeId") Long storeId,
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating);

}
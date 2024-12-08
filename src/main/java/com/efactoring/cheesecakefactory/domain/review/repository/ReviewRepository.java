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

    @Query("SELECT new com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto(" +
            "r.id, r.orders.id, r.orders.menu.id, r.orders.user.id, r.orders.store.id, r.rating, r.content, r.createdAt, r.modifiedAt) " +
            "FROM Review r " +
            "WHERE r.orders.store.id = :storeId " +
            "AND r.orders.user.id <> :userId " +
            "AND (:minRating IS NULL OR r.rating >= :minRating) " +
            "AND (:maxRating IS NULL OR r.rating <= :maxRating) " +
            "ORDER BY r.createdAt DESC")
    List<ReviewResponseDto> findAllByStoreIdWithFilters(
            @Param("storeId") Long storeId,
            @Param("userId") Long userId,
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating);


    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Review r WHERE r.orders.id = :orderId")
    boolean existsByOrdersId(Long orderId);
}


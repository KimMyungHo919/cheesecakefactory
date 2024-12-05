package com.efactoring.cheesecakefactory.domain.review.repository;

import com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto;
import com.efactoring.cheesecakefactory.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<ReviewResponseDto> findAllByStoreId(Long storeId);
}

package com.efactoring.cheesecakefactory.domain.review.service;

import com.efactoring.cheesecakefactory.domain.review.dto.ReviewRequestDto;
import com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto;
import com.efactoring.cheesecakefactory.domain.review.entity.Review;
import com.efactoring.cheesecakefactory.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    //private final OrderRepository orderRepository;

    //리뷰저장
    @Transactional
    public ReviewResponseDto saveReview(Long orderId, ReviewRequestDto requestDto) {
       // Orders orders = orderRepository.findByIdOrElseThrow(orderId);
        Review review = new Review();
        Review saveReview = reviewRepository.save(review);
        return  new ReviewResponseDto(
                saveReview.getId(),
                saveReview.getOrders().getId(),
                saveReview.getMenu().getId(),
                saveReview.getRating(),
                saveReview.getContent(),
                saveReview.getCreatedAt(),
                saveReview.getModifiedAt()
        );
    }

    //가게 리뷰 전체조회
    public List<ReviewResponseDto> getReviewsByStore(Long storeId,Integer minRating, Integer maxRating) {
        return  reviewRepository.findAllByStoreIdWithFilters(storeId,minRating,maxRating);
    }

}

package com.efactoring.cheesecakefactory.domain.review.service;

import com.efactoring.cheesecakefactory.domain.model.OrderStatus;
import com.efactoring.cheesecakefactory.domain.order.entity.Orders;
import com.efactoring.cheesecakefactory.domain.order.repository.OrderRepository;
import com.efactoring.cheesecakefactory.domain.review.dto.ReviewRequestDto;
import com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto;
import com.efactoring.cheesecakefactory.domain.review.entity.Review;
import com.efactoring.cheesecakefactory.domain.review.repository.ReviewRepository;
import com.efactoring.cheesecakefactory.domain.store.entity.Store;
import com.efactoring.cheesecakefactory.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @Transactional
    public ReviewResponseDto saveReview(Long orderId, Long userId, ReviewRequestDto requestDto) {
        boolean exists = Boolean.TRUE.equals(reviewRepository.existsByOrdersId(orderId));
        if (exists) {
            throw new IllegalArgumentException("이 주문에 대한 리뷰가 이미 존재합니다.");
        }
        Orders order = orderRepository.findByIdOrElseThrow(orderId);
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지않는 유저아이디입니다." + userId));
        Store store = order.getStore();
        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인이 주문한 건에만 리뷰를 작성할 수 있습니다.");
        }

        if (store == null) {
            throw new IllegalArgumentException("해당 주문과 연결된 가게가 존재하지 않습니다.");
        }
        if (!order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new IllegalArgumentException("완료된 주문에만 리뷰 작성이 가능합니다.");
        }

        Review review = new Review(order, requestDto.getRating(), requestDto.getContent());
        Review saveReview = reviewRepository.save(review);
        return new ReviewResponseDto(
                saveReview.getId(),
                saveReview.getOrders().getId(),
                saveReview.getMenuId(),
                saveReview.getUserId(),
                saveReview.getStoreId(),
                saveReview.getRating(),
                saveReview.getContent(),
                saveReview.getCreatedAt(),
                saveReview.getModifiedAt()
        );
    }

    public List<ReviewResponseDto> getReviewsByStore(Long storeId, Integer minRating, Integer maxRating, Long userId) {
        return reviewRepository.findAllByStoreIdWithFilters(storeId, userId, minRating, maxRating);
    }

}

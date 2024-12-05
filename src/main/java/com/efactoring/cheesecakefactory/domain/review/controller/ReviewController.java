package com.efactoring.cheesecakefactory.domain.review.controller;

import com.efactoring.cheesecakefactory.domain.review.dto.ReviewRequestDto;
import com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto;
import com.efactoring.cheesecakefactory.domain.review.repository.ReviewRepository;
import com.efactoring.cheesecakefactory.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("orders/{orderId}/reviews")
    public ResponseEntity<ReviewResponseDto> saveReview(@PathVariable("orderId") final Long orderId, @Valid @RequestBody ReviewRequestDto requestDto) {

        ReviewResponseDto responseDto = reviewService.saveReview(orderId,requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //가게의 리뷰 전체조회
    @GetMapping("orders/{storeId}/reviews")
    public List<ReviewResponseDto> findAllByStoreId(@PathVariable("storeId") final Long storeId,
                                                    @RequestParam(value = "minRating", required = false) Integer minRating,
                                                    @RequestParam(value = "maxRating", required = false) Integer maxRating
                                                    ) {
        return reviewService.getReviewsByStore(storeId,minRating,maxRating);
    }
}

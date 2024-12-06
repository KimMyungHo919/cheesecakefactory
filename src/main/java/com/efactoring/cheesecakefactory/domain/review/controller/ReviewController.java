package com.efactoring.cheesecakefactory.domain.review.controller;

import com.efactoring.cheesecakefactory.domain.common.SuccessResponseDto;
import com.efactoring.cheesecakefactory.domain.review.dto.ReviewRequestDto;
import com.efactoring.cheesecakefactory.domain.review.dto.ReviewResponseDto;
import com.efactoring.cheesecakefactory.domain.review.service.ReviewService;
import com.efactoring.cheesecakefactory.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * @param orderId
     * @param requestDto
     * @param session
     * @return ReviewResponseDto
     */
    @PostMapping("orders/{orderId}/reviews")
    public ResponseEntity<SuccessResponseDto> saveReview(@PathVariable("orderId") final Long orderId, @Valid @RequestBody ReviewRequestDto requestDto,
                                                         HttpSession session) {
        User user = (User) session.getAttribute("user");
        ReviewResponseDto responseDto = reviewService.saveReview(orderId, user.getId(), requestDto);
        SuccessResponseDto successResponseDto = new SuccessResponseDto("CREATED", 201, responseDto);

        return new ResponseEntity<>(successResponseDto, HttpStatus.CREATED);
    }

    /**
     * 특정가게 리뷰 전체조회
     * @param storeId
     * @param minRating
     * @param maxRating
     * @param request
     * @return ReviewResponseDto
     */
    @GetMapping("stores/{storeId}/reviews")
    public ResponseEntity<SuccessResponseDto> findAllByStoreId(@PathVariable("storeId") final Long storeId,
                                                               @RequestParam(value = "minRating", required = false) Integer minRating,
                                                               @RequestParam(value = "maxRating", required = false) Integer maxRating,
                                                               HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<ReviewResponseDto> reviews = reviewService.getReviewsByStore(storeId, minRating, maxRating, user.getId());

        SuccessResponseDto successResponseDto = new SuccessResponseDto("OK", 200, reviews);
        return new ResponseEntity<>(successResponseDto, HttpStatus.OK);
    }


}

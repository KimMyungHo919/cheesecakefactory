package com.efactoring.cheesecakefactory.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewRequestDto {
    @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5점 이하여야 합니다.")
    private int rating;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    @Size(min = 5, max = 1000, message = "리뷰 내용은 최소 5자에서 최대 1000자 사이여야 합니다.")
    private String  content;
}

package com.adambarnett.musicReviews.model.dtos.reviewdata;

import jakarta.validation.constraints.NotNull;

public record RequestReviewDTO(@NotNull String artistName,
                               @NotNull String albumName,
                               @NotNull Integer rating,
                               String comments,
                               @NotNull String userName) {
}

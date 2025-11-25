package com.adambarnett.musicReviews.dtos.reviewdata;

import jakarta.validation.constraints.NotNull;

public record RequestReviewDTO(@NotNull String artistName,
                               @NotNull String albumName,
                               // Need release year to create new album if album doesn't exist
                               @NotNull Integer albumReleaseYear,
                               @NotNull Integer score,
                               String comments,
                               @NotNull String username) {
}

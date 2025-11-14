package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Review;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AlbumDTO(@NotNull String title,
                       @NotNull String artist,
                       @NotNull String releaseYear,
                       List<Review> reviews) {
}

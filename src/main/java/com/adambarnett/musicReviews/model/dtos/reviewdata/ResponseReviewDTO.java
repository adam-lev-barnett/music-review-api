package com.adambarnett.musicReviews.model.dtos.reviewdata;

import com.adambarnett.musicReviews.model.Review;
import jakarta.validation.constraints.NotNull;

public record ResponseReviewDTO(Long reviewId,
                                String artistName,
                                String albumName,
                                Integer rating,
                                String comments,
                                String userName) {

    public ResponseReviewDTO(Review review) {
        this(
                review.getId(),
                review.getArtist().getArtistName(),
                review.getAlbum().getAlbumName(),
                review.getScore(),
                review.getComments(),
                review.getContributor().getUsername());
    }
}

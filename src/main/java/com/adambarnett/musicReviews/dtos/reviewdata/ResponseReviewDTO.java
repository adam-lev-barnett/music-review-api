package com.adambarnett.musicReviews.dtos.reviewdata;

import com.adambarnett.musicReviews.model.Review;

public record ResponseReviewDTO(Long id,
                                String artistName,
                                String albumName,
                                Integer score,
                                String comments,
                                String username) {

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

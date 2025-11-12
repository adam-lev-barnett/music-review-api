package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Review;

import java.util.List;

// Album doesn't have a review list field to decouple it; review list is pulled from Review.findByAlbum()
public record AlbumDTO(String title, String artist, String releaseYear, List<Review> reviews) {
}

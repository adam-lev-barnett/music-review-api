package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Review;

import java.util.List;

public record ReviewListDTO(List<Review> reviewList) {

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Review review : reviewList) {
            sb.append(review.toString()).append("\n");
        }
        return sb.toString();
    }
}

package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Review;
import com.adambarnett.musicReviews.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByContributor(String contributorUsername) {
        return reviewRepository.findByContributor_Username(contributorUsername);
    }

    public List<Review> getReviewsByArtistName(String artistName) {
        List<Review> reviews = reviewRepository.findByArtistName(artistName);
        return reviewRepository.findByArtistName(artistName);
    }

}

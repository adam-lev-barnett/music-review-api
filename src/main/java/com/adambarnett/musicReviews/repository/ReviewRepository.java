package com.adambarnett.musicReviews.repository;

import com.adambarnett.musicReviews.model.Review;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByArtistName(String artistName);
    List<Review> findBySubmittedBy(String username);
    List<Review> findReviewByScore(Integer rating);
    List<Review> findByAlbumName(String albumName);
    List<Review> findByArtistNameAndAlbumName(String artistName, String albumName);


    List<Review> sortByRating(Sort sort);
    List<Review> sortByArtistAndByAlbum(Sort sort);


    List<Review> findByContributor_Username(String contributorUsername);
}

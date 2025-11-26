package com.adambarnett.musicReviews.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByArtist_ArtistName(String artistName);
    List<Review> findByAlbum_AlbumName(String albumName);
    List<Review> findByArtist_ArtistNameAndAlbum_AlbumName(String artistName, String albumName);

    List<Review> findByScore(Integer score);
    List<Review> findByScoreGreaterThan(Integer score);
    List<Review> findByScoreLessThan(Integer score);
    List<Review> findByContributor_Username(String contributorUsername);
}

package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.dtos.reviewdata.RequestReviewDTO;
import com.adambarnett.musicReviews.model.dtos.reviewdata.ResponseReviewDTO;
import com.adambarnett.musicReviews.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("reviews")
    public List<ResponseReviewDTO> getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("reviews/artist/{artistName}")
    public List<ResponseReviewDTO> getReviewsByArtist(@PathVariable("artistName") String artistName) {
        return reviewService.findByArtistName(artistName);
    }

    @GetMapping("reviews/albums/{albumName}")
    public List<ResponseReviewDTO> getReviewsByAlbumName(@PathVariable("albumName") String albumName) {
        return reviewService.findByAlbumName(albumName);
    }

    @GetMapping("reviews/{artist}/{album}")
    public List<ResponseReviewDTO> getReviewsByAlbum(@PathVariable("artist") String artist, @PathVariable("album") String album) {
        return  reviewService.findByArtistNameAndAlbumName(artist, album);
    }

    @GetMapping("reviews/contributors/{username}")
    public List<ResponseReviewDTO> getReviewsByContributor(@PathVariable("username") String username) {
        return reviewService.findByContributor(username);
    }

    @GetMapping("reviews/score/{score}")
    public List<ResponseReviewDTO> getReviewsByScore(@PathVariable("score") Integer score) {
        return reviewService.findByScore(score);
    }

    @GetMapping("reviews/score/greaterthan/{score}")
    public List<ResponseReviewDTO> getReviewsByScoreGreaterThan(@PathVariable("score") Integer score) {
        return reviewService.findByScoreGreaterThan(score);
    }

    @GetMapping("reviews/score/lessthan/{score}")
    public List<ResponseReviewDTO> getReviewsByScoreLessThan(@PathVariable("score") Integer score) {
        return reviewService.findByScoreLessThan(score);
    }

    @PostMapping("reviews")
    public ResponseReviewDTO createReview(@RequestBody RequestReviewDTO reviewDTO) throws InvalidArgumentException {
        return reviewService.addReview(reviewDTO);
    }

}

package com.adambarnett.musicReviews.review;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.review.reviewdata.RequestReviewDTO;
import com.adambarnett.musicReviews.review.reviewdata.ResponseReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ResponseReviewDTO> getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/artist/{artistName}")
    public List<ResponseReviewDTO> getReviewsByArtist(@PathVariable("artistName") String artistName) {
        return reviewService.findByArtistName(artistName);
    }

    @GetMapping("/artist/{artistName}/album/{albumName}")
    public List<ResponseReviewDTO> getReviewsByArtistAndAlbum(@PathVariable("artistName") String artist, @PathVariable("albumName") String album) {
        return  reviewService.findByArtistNameAndAlbumName(artist, album);
    }

    @GetMapping("/contributor/{username}")
    public List<ResponseReviewDTO> getReviewsByContributor(@PathVariable("username") String username) {
        return reviewService.findByContributor(username);
    }

    @GetMapping("/score/{score}")
    public List<ResponseReviewDTO> getReviewsByScore(@PathVariable("score") Integer score) {
        return reviewService.findByScore(score);
    }

    @GetMapping("/score/greater-than/{score}")
    public List<ResponseReviewDTO> getReviewsByScoreGreaterThan(@PathVariable("score") Integer score) {
        return reviewService.findByScoreGreaterThan(score);
    }

    @GetMapping("/score/less-than/{score}")
    public List<ResponseReviewDTO> getReviewsByScoreLessThan(@PathVariable("score") Integer score) {
        return reviewService.findByScoreLessThan(score);
    }

    @PostMapping
    public ResponseReviewDTO createReview(@RequestBody RequestReviewDTO reviewDTO) throws InvalidArgumentException {
        return reviewService.addReview(reviewDTO);
    }

}

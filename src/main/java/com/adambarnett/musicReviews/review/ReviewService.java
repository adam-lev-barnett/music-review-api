package com.adambarnett.musicReviews.review;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.album.Album;
import com.adambarnett.musicReviews.artist.Artist;
import com.adambarnett.musicReviews.contributor.Contributor;
import com.adambarnett.musicReviews.review.reviewdata.RequestReviewDTO;
import com.adambarnett.musicReviews.review.reviewdata.ResponseReviewDTO;
import com.adambarnett.musicReviews.album.AlbumRepository;
import com.adambarnett.musicReviews.artist.ArtistRepository;
import com.adambarnett.musicReviews.contributor.ContributorRepository;
import com.adambarnett.musicReviews.utility.EntityCreationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ContributorRepository contributorRepository;
    private final EntityCreationHelper entityCreationHelper;

    public ResponseReviewDTO addReview(RequestReviewDTO reviewDTO) throws InvalidArgumentException {
        if (reviewDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review is null");
        }
        Review newReview = new Review();
        Contributor contributor = entityCreationHelper.getOrCreateContributor(reviewDTO.username());

        newReview.setContributor(contributor);

        // Check if we need to add a new artist to the repository
        Artist reviewedArtist = entityCreationHelper.getOrCreateArtist(reviewDTO.artistName());
        newReview.setArtist(reviewedArtist);
        
        // Check if we need to add a new album to the repository
        // Add that album to new artist list
        // Avoiding adding albumService logic 
        Album reviewedAlbum = entityCreationHelper.getOrCreateAlbum(reviewDTO, reviewedArtist);
        newReview.setAlbum(reviewedAlbum);
        newReview.setScore(reviewDTO.score());
        newReview.setComments(reviewDTO.comments());
        return new ResponseReviewDTO(reviewRepository.save(newReview));
    }

    // Default ordering should be by Artist name and then albums by release year
    public List<ResponseReviewDTO> getReviews() {
        List<Review> reviewList =  reviewRepository.findAll(
                                                        Sort.by("artist.artistName").ascending()
                                                        .and(Sort.by("album.releaseYear").ascending())
        );
        return createReviewDTOList(reviewList);
    }

    // Pulls up all reviews from a single user
    public List<ResponseReviewDTO> findByContributor(String username) {
        Contributor contributor = contributorRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Review> reviewList = reviewRepository.findByContributor_Username(contributor.getUsername());
        return createReviewDTOList(reviewList);
    }

    public List<ResponseReviewDTO> findByArtistName(String artistName) {
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        List<Review> reviewList = reviewRepository.findByArtist_ArtistName(artist.getArtistName());
        return createReviewDTOList(reviewList);
    }

    public List<ResponseReviewDTO> findByAlbumName(String albumName) {
        Album album = albumRepository.findByAlbumName(albumName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));
        List<Review> reviewList = reviewRepository.findByAlbum_AlbumName(album.getAlbumName());
        return createReviewDTOList(reviewList);
    }

    // If someone is looking for a specific album; can't just be by album name because multiple albums can be named the same thing
    public List<ResponseReviewDTO> findByArtistNameAndAlbumName(String artistName, String albumName) {
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        Album album = albumRepository.findByAlbumName(albumName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));
        List<Review> reviewList = reviewRepository.findByArtist_ArtistNameAndAlbum_AlbumName(artist.getArtistName(), album.getAlbumName());
        return createReviewDTOList(reviewList);
    }

    public List<ResponseReviewDTO> findByScore(Integer score) {
        return createReviewDTOList(reviewRepository.findByScore(score));
    }

    public List<ResponseReviewDTO> findByScoreGreaterThan(Integer score) {
        return createReviewDTOList(reviewRepository.findByScoreGreaterThan(score));
    }

    public List<ResponseReviewDTO> findByScoreLessThan(Integer score) {
        List<Review> reviewList =  reviewRepository.findByScoreLessThan(score);
        return createReviewDTOList(reviewList);
    }

    // Turns returned Review list into list of DTOs
    private static List<ResponseReviewDTO> createReviewDTOList(List<Review> reviewList) {
        List<ResponseReviewDTO> responseReviewDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            responseReviewDTOList.add(new ResponseReviewDTO(review));
        }
        return responseReviewDTOList;
    }

}

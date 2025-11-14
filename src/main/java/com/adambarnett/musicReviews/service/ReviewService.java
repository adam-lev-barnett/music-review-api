package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.model.Review;
import com.adambarnett.musicReviews.repository.AlbumRepository;
import com.adambarnett.musicReviews.repository.ArtistRepository;
import com.adambarnett.musicReviews.repository.ContributorRepository;
import com.adambarnett.musicReviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ContributorRepository contributorRepository;

    public Review addReview(Review review) {
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review is null");
        }
        Review newReview = new Review();
        newReview.setContributor(review.getContributor());
        
        // Check if we need to add a new artist to the repository
        Artist reviewedArtist = createArtistFromReview(review);
        newReview.setArtist(reviewedArtist);
        
        // Check if we need to add a new album to the repository
        // Add that album to new artist list
        // Avoiding adding albumService logic 
        Album reviewedAlbum = createAlbumFromReview(review, reviewedArtist);
        newReview.setAlbum(reviewedAlbum);
        newReview.setScore(review.getScore());
        return reviewRepository.save(newReview);
    }

    // Default ordering should be by Artist name and then albums by release year
    public List<Review> getReviews() {
        return reviewRepository.findAll(
                Sort.by("artist.artistName").ascending()
                        .and(Sort.by("album.releaseYear").ascending())
        );
    }

    // Pulls up all reviews from a single user
    public List<Review> findByContributor(String username) {
        Contributor contributor = contributorRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return reviewRepository.findByContributor_Username(contributor.getUsername());
    }

    public List<Review> findByArtistName(String artistName) {
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        return reviewRepository.findByArtist_ArtistName(artist.getArtistName());
    }

    public List<Review> findByAlbumName(String albumName) {
        Album album = albumRepository.findByAlbumName(albumName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));
        return reviewRepository.findByAlbum_AlbumName(album.getAlbumName());
    }

    // If someone is looking for a specific album; can't just be by album name because multiple albums can be named the same thing
    public List<Review> findByArtistNameAndAlbumName(String artistName, String albumName) {
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        Album album = albumRepository.findByAlbumName(albumName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found"));
        return reviewRepository.findByArtist_ArtistNameAndAlbum_AlbumName(artist.getArtistName(), album.getAlbumName());
    }

    public List<Review> findByScore(Integer score) {
        return reviewRepository.findByScore(score);
    }

    public List<Review> findByScoreGreaterThan(Integer score) {
        return reviewRepository.findByScoreGreaterThan(score);
    }

    public List<Review> findByScoreLessThan(Integer score) {
        return reviewRepository.findByScoreLessThan(score);
    }

    private Artist createArtistFromReview(Review review) {
        Artist returnArtist = review.getArtist();
        Optional<Artist> artistOptional = artistRepository.findByArtistName(review.getArtist().getArtistName());
        if (!artistOptional.isPresent()) {
            Artist newArtist = new Artist();
            newArtist.setArtistName(returnArtist.getArtistName());
            artistRepository.save(newArtist);
            System.out.println("New artist was created: " + newArtist.getArtistName());
            return newArtist;
        }
        return artistOptional.get();
    }

    private Album createAlbumFromReview(Review review, Artist artist) {
        Album returnAlbum = review.getAlbum();
        Optional<Album> albumOptional = albumRepository.findByAlbumNameAndArtist_ArtistName(returnAlbum.getAlbumName(), artist.getArtistName());
        if (albumOptional.isPresent()) {
            return albumOptional.get();
        }
        Album newAlbum = new Album();
        newAlbum.setAlbumName(returnAlbum.getAlbumName());
        newAlbum.setArtist(artist);
        newAlbum.setReleaseYear(returnAlbum.getReleaseYear());
        artist.getAlbums().add(newAlbum);
        System.out.println("New album '" + newAlbum.getAlbumName() + "' was created" );
        return albumRepository.save(newAlbum);

    }



}

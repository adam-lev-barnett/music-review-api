package com.adambarnett.musicReviews.utility;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.album.Album;
import com.adambarnett.musicReviews.artist.Artist;
import com.adambarnett.musicReviews.contributor.Contributor;
import com.adambarnett.musicReviews.review.reviewdata.RequestReviewDTO;
import com.adambarnett.musicReviews.album.AlbumRepository;
import com.adambarnett.musicReviews.artist.ArtistRepository;
import com.adambarnett.musicReviews.contributor.ContributorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EntityCreationHelper {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final ContributorRepository contributorRepository;

    public Contributor getOrCreateContributor(String username) {
        return contributorRepository.findByUsername(username)
                .orElseGet(() -> {
                    Contributor newContributor = new Contributor();
                    newContributor.setUsername(username);
                    return contributorRepository.save(newContributor);
                });
    }

    public Artist getOrCreateArtist(String artistName) {
        return artistRepository.findByArtistName(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    artistRepository.save(newArtist);
                    System.out.println("Artist did not exist. New artist was created: " + newArtist.getArtistName());
                    return newArtist;
                });
    }

    /** Checks if album exists, updates artist average score, and returns the album if found. Creates and returns new album if not.*/
    public Album getOrCreateAlbum(RequestReviewDTO reviewDTO, Artist artist) throws InvalidArgumentException {
        Optional<Album> albumOptional = albumRepository.findByAlbumNameAndArtist_ArtistName(reviewDTO.albumName(), artist.getArtistName());
        if (albumOptional.isPresent()) {
            return albumOptional.get();
        }
        Album newAlbum = new Album();
        newAlbum.setAlbumName(reviewDTO.albumName());
        newAlbum.setArtist(artist);
        newAlbum.setReleaseYear(reviewDTO.albumReleaseYear());
        // Updates album score with an initial review count of 1 and its first review score;
        // average will continue to calculate upon more review submissions for the album
        newAlbum.getReviewNumberAndCount()[0] = 1;
        newAlbum.getReviewNumberAndCount()[1] = reviewDTO.score();

        // Ensure artist knows about the albums it owns
        artist.addAlbum(newAlbum);
        System.out.println("New album '" + newAlbum.getAlbumName() + "' was created" );
        return albumRepository.save(newAlbum);
    }



}

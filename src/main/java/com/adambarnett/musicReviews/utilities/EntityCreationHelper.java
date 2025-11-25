package com.adambarnett.musicReviews.utilities;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.dtos.reviewdata.RequestReviewDTO;
import com.adambarnett.musicReviews.repository.AlbumRepository;
import com.adambarnett.musicReviews.repository.ArtistRepository;
import com.adambarnett.musicReviews.repository.ContributorRepository;
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

    public Album getOrCreateAlbum(RequestReviewDTO reviewDTO, Artist artist) throws InvalidArgumentException {
        Optional<Album> albumOptional = albumRepository.findByAlbumNameAndArtist_ArtistName(reviewDTO.albumName(), artist.getArtistName());
        if (albumOptional.isPresent()) {
            return albumOptional.get();
        }
        Album newAlbum = new Album();
        newAlbum.setAlbumName(reviewDTO.albumName());
        newAlbum.setArtist(artist);
        newAlbum.setReleaseYear(reviewDTO.albumReleaseYear());
        artist.addAlbum(newAlbum);
        System.out.println("New album '" + newAlbum.getAlbumName() + "' was created" );
        return albumRepository.save(newAlbum);
    }


}

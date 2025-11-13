package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.repository.ArtistRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;


    public ArtistService(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist getArtistByName(String artistName) {
        Optional<Artist> artistOptional = artistRepository.findByArtistName(artistName);
        if (!artistOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        return artistOptional.get();
    }

    public Artist addArtist(String artistName) {
        Optional<Artist> artistOptional = this.artistRepository.findByArtistName(artistName);
        if (artistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist already exists");
        }
        Artist artist = new Artist();
        artist.setArtistName(artistName);
        artistRepository.save(artist);
        System.out.println("Entry successfully created for " + artistName);
        return artist;
    }

    public Artist updateArtist(Long id, Artist updatedArtist) {
        Optional<Artist> artistOptional = getArtistOptionalOrNotFound(id);
        Artist artist = artistOptional.get();
        artist.setArtistName(updatedArtist.getArtistName());
        return artistRepository.save(artist);
    }

    public Artist deleteArtist(Long id) {
        Optional<Artist> artistOptional = getArtistOptionalOrNotFound(id);
        Artist deletedArtist = artistOptional.get();
        artistRepository.delete(deletedArtist);
        return deletedArtist;
    }

    private Optional<Artist> getArtistOptionalOrNotFound(Long id) {
        Optional<Artist> artistOptional = this.artistRepository.findById(id);
        if (!artistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        }
        return artistOptional;
    }

    public List<Album> getAlbumsByArtistName(String artistName) {
        Artist artist = getArtistByName(artistName);
        return artist.getAlbums();
    }

}

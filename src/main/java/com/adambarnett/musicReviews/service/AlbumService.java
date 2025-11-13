package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.repository.AlbumRepository;
import com.adambarnett.musicReviews.repository.ArtistRepository;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;


    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    // If artist doesn't exist, add it to the artist repository
    public Album addAlbum(Album newAlbum) {
        //TODO avoid duplicates and ignore case
        String artistName = newAlbum.getArtist().getArtistName();
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    System.out.println("Artist does not exist in database. Adding artist: " + artistName);
                    return artistRepository.save(newArtist);
                });
        artist.getAlbums().add(newAlbum);
        albumRepository.save(newAlbum);
        System.out.println("Successfully added album " + newAlbum.getAlbumName() + " by " + newAlbum.getArtist().getArtistName() + " to database.");
        return newAlbum;
    }

    public Album updateAlbum(Long id, Album updatedAlbum) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (!albumOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update album. Album not found");
        Album existingAlbum = albumOptional.get();
        existingAlbum.setAlbumName(updatedAlbum.getAlbumName());
        existingAlbum.setArtist(updatedAlbum.getArtist());
        existingAlbum.setReleaseYear(updatedAlbum.getReleaseYear());
        return albumRepository.save(existingAlbum);
    }

    public Album deleteAlbum(Long id) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (albumOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot delete album. Album not found");
        Album deletedAlbum = albumOptional.get();
        albumRepository.deleteById(id);
        // Delete the album from the associated artist's album list
        Artist artist = deletedAlbum.getArtist();
        if (artist != null) {
            artist.getAlbums().remove(deletedAlbum);
        }
        return deletedAlbum;
    }

    public List<Album> findByString(String field, String filterBy) {

        List<Album> searchResult;

        switch (filterBy) {
            case "artistName":
                if (!artistRepository.existsByArtistName(field)) {
                    throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist does not exist in database.");
                }
                searchResult = albumRepository.findByArtist_ArtistName(field);
                break;

            case "albumName":
                if (!albumRepository.existsByAlbumName((field))) {
                    throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Album does not exist in database.");
                }
                searchResult = albumRepository.findByAlbumName(field);
                break;

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter. Please use artistName or albumName.");
        }
        return searchResult;
    }

    public List<Album> findByReleaseYear(Integer releaseYear) {

        List<Album> albumsByYear = albumRepository.findByReleaseYear(releaseYear);
        if (albumsByYear.isEmpty()) {
            System.err.println("No albums found for given year.");
        }
        return albumsByYear;
    }

    public List<Album> findAllSortedByArtist() {
        return albumRepository.findAll(Sort.by(Sort.Direction.ASC, "artist.artistName"));

    }

    public List<Album> findAllSortedByReleaseYear() {
        return albumRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseYear"));
    }



}

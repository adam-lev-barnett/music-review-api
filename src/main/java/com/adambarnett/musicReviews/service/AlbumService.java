package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.repository.AlbumRepository;
import com.adambarnett.musicReviews.repository.ArtistRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;


    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    // If artist doesn't exist, add it to the artist repository
    public void addAlbum(String albumName, String artistName, Integer releaseYear) {
        //TODO avoid duplicates and ignore case
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    System.out.println("Artist does not exist in database. Adding artist: " + artistName);
                    return artistRepository.save(newArtist);
                });
        Album album = new Album();
        album.setAlbumName(albumName);
        album.setArtist(artist);
        album.setReleaseYear(releaseYear);
        artist.getAlbums().add(album);
        albumRepository.save(album);
        System.out.println("Successfully added album " + albumName + " by " + artistName + " to database.");
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
                    throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist does not exist in database.");
                }
                searchResult = albumRepository.findByAlbumName(field);
                break;

            default:
                throw new   ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter. Please use artistName or albumName.");
        }
        return searchResult;
    }

    public List<Album> findByReleaseYear(Integer releaseYear) {

        List<Album> albumsByYear = albumRepository.findByReleaseYear(releaseYear);
        if (!albumsByYear.isEmpty()) {
            System.err.println("No albums found for given year.");
        }
        return albumsByYear;
    }



}

package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.dtos.AlbumDTO;
import com.adambarnett.musicReviews.repository.AlbumRepository;
import com.adambarnett.musicReviews.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    // If artist doesn't exist, add it to the artist repository
    public Album addAlbum(Album newAlbum) throws InvalidArgumentException {
        //TODO avoid duplicates and ignore case
        String artistName = newAlbum.getArtist().getArtistName();
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    System.out.println("Artist does not exist in database. Adding artist: " + artistName);
                    return artistRepository.save(newArtist);
                });
        Album album = albumRepository.findByAlbumNameAndArtist_ArtistName(newAlbum.getAlbumName(), artistName)
                .orElseGet(() -> {
                    Album createAlbum = new Album();
                    createAlbum.setAlbumName(newAlbum.getAlbumName());
                    createAlbum.setArtist(artist);
                    createAlbum.setReleaseYear(newAlbum.getReleaseYear());
                    return albumRepository.save(createAlbum);
                });
        artist.addAlbum(album);
        System.out.println("Successfully added album " + newAlbum.getAlbumName() + " by " + newAlbum.getArtist().getArtistName() + " to database.");
        return album;
    }

    public Album updateAlbum(Long id, Album toUpdate) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (!albumOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update album. Album not found");
        Album existingAlbum = albumOptional.get();
        existingAlbum.setAlbumName(toUpdate.getAlbumName());
        existingAlbum.setReleaseYear(toUpdate.getReleaseYear());
        Artist artistUpdate = artistRepository.findById(toUpdate.getArtist().getId())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(toUpdate.getArtist().getArtistName());
                    return artistRepository.save(newArtist);
                });

        existingAlbum.setArtist(artistUpdate);

        return albumRepository.save(existingAlbum);
    }

    public Album deleteAlbum(Long id) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (albumOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot delete album. Album not found");
        Album deletedAlbum = albumOptional.get();
        // Delete the album from the associated artist's album list
        Artist artist = deletedAlbum.getArtist();
        if (artist != null) {
            artist.getAlbums().remove(deletedAlbum);
        }
        albumRepository.delete(deletedAlbum);
        return deletedAlbum;
    }

    public List<Album> findByArtistName(String artistName) {
        return albumRepository.findByArtist_ArtistName(artistName);
    }

    public Album findByAlbumNameAndArtistName(String albumName, String artistName) {
        return albumRepository.findByAlbumNameAndArtist_ArtistName(albumName, artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find artist and album: " + artistName + " and " + albumName));
    }

    public List<Album> findByReleaseYear(Integer releaseYear) {

        List<Album> albumsByYear = albumRepository.findByReleaseYear(releaseYear);
        if (albumsByYear.isEmpty()) {
            System.err.println("No albums found for given year.");
        }
        return albumsByYear;
    }

    public List<Album> sortAll(String sort) {
        switch (sort) {
            case "artistName":
                return albumRepository.findAll(Sort.by(Sort.Direction.ASC, "artist.artistName"));
            case "releaseYear":
                return albumRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseYear"));
            //TODO add sort by average score
            default:
                return albumRepository.findAll(Sort.by(Sort.Direction.ASC, "albumName"));
        }
    }

    public Album getAlbumByAlbumName(String albumName) {
        return albumRepository.findByAlbumName(albumName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find album with name: " + albumName));
    }

}

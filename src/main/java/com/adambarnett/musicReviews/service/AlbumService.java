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
    public AlbumDTO addAlbum(AlbumDTO newAlbumDTO) throws InvalidArgumentException {
        //TODO avoid duplicates and ignore case
        String artistName = newAlbumDTO.artist().getArtistName();
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    System.out.println("Artist does not exist in database. Adding artist: " + artistName);
                    return artistRepository.save(newArtist);
                });
        Album album = albumRepository.findByAlbumNameAndArtist_ArtistName(newAlbumDTO.albumName(), artistName)
                        .orElseGet(() -> {
                            Album newAlbum = new Album();
                            newAlbum.setAlbumName(newAlbumDTO.albumName());
                            newAlbum.setArtist(artist);
                            newAlbum.setReleaseYear(newAlbumDTO.releaseYear());
                            return albumRepository.save(newAlbum);
                        });
        artist.addAlbum(album);
        return new AlbumDTO(album);
    }

    public AlbumDTO updateAlbum(AlbumDTO toUpdate) {
        Album existingAlbum = albumRepository.findById(toUpdate.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update album. Album not found."));
        existingAlbum.setAlbumName(toUpdate.albumName());
        existingAlbum.setReleaseYear(toUpdate.releaseYear());
        Artist artistUpdate = artistRepository.findById(toUpdate.artist().getId())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(toUpdate.artist().getArtistName());
                    return artistRepository.save(newArtist);
                });
        existingAlbum.setArtist(artistUpdate);

        return new AlbumDTO(albumRepository.save(existingAlbum));
    }

    public AlbumDTO deleteAlbum(Long id) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (albumOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot delete album. Album not found");
        Album deletedAlbum = albumOptional.get();
        // Delete the album from the associated artist's album list
        Artist artist = deletedAlbum.getArtist();
        if (artist != null) {
            artist.getAlbums().remove(deletedAlbum);
        }
        albumRepository.delete(deletedAlbum);
        return new AlbumDTO(deletedAlbum);
    }

    public List<Album> findByArtistName(String artistName) {
        return albumRepository.findByArtist_ArtistName(artistName);
    }

    public AlbumDTO findByAlbumNameAndArtistName(String albumName, String artistName) {
        return new AlbumDTO(albumRepository.findByAlbumNameAndArtist_ArtistName(albumName, artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find artist and album: " + artistName + " and " + albumName)));
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

package com.adambarnett.musicReviews.album;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.artist.Artist;
import com.adambarnett.musicReviews.album.albumdata.RequestAlbumDTO;
import com.adambarnett.musicReviews.album.albumdata.ResponseAlbumDTO;
import com.adambarnett.musicReviews.artist.ArtistRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    // If artist doesn't exist, add it to the artist repository
    public ResponseAlbumDTO addAlbum(RequestAlbumDTO newAlbum) throws InvalidArgumentException {
        //TODO avoid duplicates and ignore case
        if (newAlbum == null) throw new InvalidArgumentException("Album cannot be null");
        if (newAlbum.releaseYear() == null || newAlbum.releaseYear() < 0) throw new InvalidArgumentException("ReleaseYear must be greater than 0");
        String artistName = newAlbum.artistName();
        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    System.out.println("Artist does not exist in database. Adding artist: " + artistName);
                    return artistRepository.save(newArtist);
                });
        Album album = albumRepository.findByAlbumNameAndArtist_ArtistName(newAlbum.albumName(), artistName)
                .orElseGet(() -> {
                    Album createAlbum = new Album();
                    createAlbum.setAlbumName(newAlbum.albumName());
                    createAlbum.setArtist(artist);
                    createAlbum.setReleaseYear(newAlbum.releaseYear());
                    return albumRepository.save(createAlbum);
                });
        artist.addAlbum(album);
        System.out.println("Successfully added album " + newAlbum.albumName() + " by " + newAlbum.artistName() + " to database.");
        return new ResponseAlbumDTO(album);
    }

    public ResponseAlbumDTO updateAlbum(Long id, RequestAlbumDTO toUpdate) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (!albumOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update album. Album not found");
        Album existingAlbum = albumOptional.get();
        existingAlbum.setAlbumName(toUpdate.albumName());
        existingAlbum.setReleaseYear(toUpdate.releaseYear());
        Artist artistUpdate = artistRepository.findByArtistName(toUpdate.artistName())
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(toUpdate.artistName());
                    return artistRepository.save(newArtist);
                });

        // Wait until the artist is actually updated.
        existingAlbum.setArtist(artistUpdate);

        return new ResponseAlbumDTO(albumRepository.save(existingAlbum));
    }

    public ResponseAlbumDTO deleteAlbum(Long id) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if (albumOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot delete album. Album not found");
        Album deletedAlbum = albumOptional.get();
        // Delete the album from the associated artist's album list
        Artist artist = deletedAlbum.getArtist();
        if (artist != null) {
            artist.getAlbums().remove(deletedAlbum);
        }
        albumRepository.delete(deletedAlbum);
        return new ResponseAlbumDTO(deletedAlbum);
    }

    public List<ResponseAlbumDTO> findByArtistName(String artistName) {

        if (artistName == null || artistName.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist name cannot be null or empty");

        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find artist with name " + artistName));

        List<ResponseAlbumDTO> albumDtos = new ArrayList<>();
        for (Album album : albumRepository.findByArtist_ArtistName(artist.getArtistName())) {
            albumDtos.add(new ResponseAlbumDTO(album));
        }
        albumDtos.sort(Comparator.comparingInt(ResponseAlbumDTO::releaseYear));
        return albumDtos;
    }

    public ResponseAlbumDTO findByAlbumNameAndArtistName(String albumName, String artistName) {
        if (artistName == null || artistName.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist name cannot be null or empty");
        if (albumName == null || albumName.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Album name cannot be null or empty");

        Artist artist = artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find artist with name " + artistName));

        return new ResponseAlbumDTO(albumRepository.findByAlbumNameAndArtist_ArtistName(albumName, artist.getArtistName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find artist and album: " + artist.getArtistName() + " and " + albumName)));
    }

    public List<ResponseAlbumDTO> findByReleaseYear(Integer releaseYear) {

        List<Album> albumsByYear = albumRepository.findByReleaseYear(releaseYear);
        if (albumsByYear.isEmpty()) {
            System.err.println("No albums found for given year.");
        }
        return convertToResponseAlbumDTOList(albumsByYear);
    }

    public List<ResponseAlbumDTO> sortAll(String sort) {
        switch (sort) {
            case "artistName":
                return convertToResponseAlbumDTOList(albumRepository.findAll(Sort.by(Sort.Direction.ASC, "artist.artistName")));
            case "releaseYear":
                return convertToResponseAlbumDTOList(albumRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseYear")));
            //TODO add sort by average score
            default:
                return convertToResponseAlbumDTOList(albumRepository.findAll(Sort.by(Sort.Direction.ASC, "albumName")));
        }
    }

    public List<ResponseAlbumDTO> getAlbumsByAlbumName(String albumName) {

        List<ResponseAlbumDTO> albumDtos = new ArrayList<>();
        for (Album album : albumRepository.findByAlbumName(albumName)) {
            albumDtos.add(new ResponseAlbumDTO(album));
        }
        albumDtos.sort(Comparator.comparing(ResponseAlbumDTO::artistName));
        return albumDtos;

    }

    private List<ResponseAlbumDTO> convertToResponseAlbumDTOList(List<Album> albums) {
        List<ResponseAlbumDTO> responseAlbumDTOs = new ArrayList<>();
        for (Album album : albums) {
            responseAlbumDTOs.add(new ResponseAlbumDTO(album));
        }
        return responseAlbumDTOs;
    }

}

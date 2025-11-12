package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.dtos.ArtistDTO;
import com.adambarnett.musicReviews.repository.AlbumRepository;
import com.adambarnett.musicReviews.repository.ArtistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;


    public ArtistService(final ArtistRepository artistRepository, final AlbumRepository albumRepository, final AlbumService albumService) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    //TODO Add an addToAlbumList to add albums in addition to what's currently there
    // Returns list of albums without coupling Artist to Album
    public List<String> getAlbumList(String artistName) {
        List<Album> albumListTemp = albumRepository.findByArtist_ArtistName(artistName);
        List<String> albumNameList = new ArrayList<>();
        for (Album album : albumListTemp) {
            albumNameList.add(album.getAlbumName());
        }
        return albumNameList;
    }

    public void addArtist(String artistName) {
        Artist artist;
        Optional<Artist> artistOptional = this.artistRepository.findByArtistName(artistName);
        if (!artistOptional.isPresent()) {
            artist = new Artist();
            artist.setArtistName(artistName);
        }
        else {
            artist = artistOptional.get();
        }
        artistRepository.save(artist);
        System.out.println("Entry successfully created for " + artistName);
    }

    //Returns ArtistDTO with artist name and list of albums
    public ArtistDTO getArtistDetails(String artistName) {
        Optional<Artist> artistOptional = this.artistRepository.findByArtistName(artistName);
        if (!artistOptional.isPresent()) {
            return null;
        }
        return new ArtistDTO(artistName, getAlbumList(artistName));
    }

    public void deleteArtist(String artistName) {
        Optional<Artist> artistOptional = this.artistRepository.findByArtistName(artistName);
        if (!artistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        }
        Artist artist = artistOptional.get();
        artistRepository.delete(artist);
    }



}

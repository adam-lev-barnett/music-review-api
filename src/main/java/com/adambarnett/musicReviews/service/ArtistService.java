package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.model.dtos.albumdata.ResponseAlbumDTO;
import com.adambarnett.musicReviews.model.dtos.artistdata.RequestArtistDTO;
import com.adambarnett.musicReviews.model.dtos.artistdata.ResponseArtistDTO;
import com.adambarnett.musicReviews.model.dtos.contributordata.ResponseContributorDTO;
import com.adambarnett.musicReviews.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ResponseArtistDTO getArtistByName(String artistName) throws ResponseStatusException {
        Optional<Artist> artistOptional = artistRepository.findByArtistName(artistName);
        if (!artistOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        return new ResponseArtistDTO(artistOptional.get());
    }

    public ResponseArtistDTO addArtist(RequestArtistDTO artistDTO) throws ResponseStatusException {
        Optional<Artist> artistOptional = this.artistRepository.findByArtistName(artistDTO.artistName());
        if (artistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist already exists");
        }
        Artist artist = new Artist();
        artist.setArtistName(artistDTO.artistName());
        artistRepository.save(artist);
        System.out.println("Entry successfully created for " + artistDTO.artistName());
        return new ResponseArtistDTO(artist);
    }

    public ResponseArtistDTO updateArtist(Long id, RequestArtistDTO updatedArtist) throws ResponseStatusException {
        Optional<Artist> artistOptional = getArtistOptionalOrNotFound(id);
        Artist artist = artistOptional.get();
        artist.setArtistName(updatedArtist.artistName());
        return new ResponseArtistDTO(artistRepository.save(artist));
    }

    public ResponseArtistDTO deleteArtist(Long id) throws ResponseStatusException {
        Optional<Artist> artistOptional = getArtistOptionalOrNotFound(id);
        Artist deletedArtist = artistOptional.get();
        artistRepository.delete(deletedArtist);
        return new ResponseArtistDTO(deletedArtist);
    }

    public List<ResponseArtistDTO> getArtists() {
        List<Artist> artistList = this.artistRepository.findAll(Sort.by(Sort.Direction.ASC, "artistName"));
        List<ResponseArtistDTO> responseArtistDTOList = new ArrayList<>();
        for (Artist artist : artistList) {
            responseArtistDTOList.add(new ResponseArtistDTO(artist));
        }
        return responseArtistDTOList;
    }

    public List<ResponseContributorDTO> getFavoritedBy(String artistName) {
        Artist favoritedArtist =  artistRepository.findByArtistName(artistName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"));
        return favoritedArtist.getFavoritedBy();
    }

    // Simplifies repetitive lookups
    private Optional<Artist> getArtistOptionalOrNotFound(Long id) {
        Optional<Artist> artistOptional = this.artistRepository.findById(id);
        if (!artistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        }
        return artistOptional;
    }

}

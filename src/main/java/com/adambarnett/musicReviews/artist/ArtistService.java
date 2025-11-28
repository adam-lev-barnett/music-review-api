package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.artist.artistdata.RequestArtistDTO;
import com.adambarnett.musicReviews.artist.artistdata.ResponseArtistDTO;

import lombok.RequiredArgsConstructor;
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
        if (artistName == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist name is null");
        Optional<Artist> artistOptional = artistRepository.findByArtistName(artistName);
        if (!artistOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        return new ResponseArtistDTO(artistOptional.get());
    }

    public ResponseArtistDTO addArtist(RequestArtistDTO artistDTO) throws ResponseStatusException {
        if (artistDTO == null || artistDTO.artistName().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Artist name cannot be null or empty");
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

    // Simplifies repetitive lookups
    private Optional<Artist> getArtistOptionalOrNotFound(Long id) {
        Optional<Artist> artistOptional = this.artistRepository.findById(id);
        if (!artistOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        }
        return artistOptional;
    }

}

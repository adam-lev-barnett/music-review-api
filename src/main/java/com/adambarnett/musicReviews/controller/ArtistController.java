package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.dtos.artistdata.ResponseArtistDTO;
import com.adambarnett.musicReviews.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("artists/{artistName}")
    public Artist getArtistByName(@PathVariable String artistName) {
        return artistService.getArtistByName(artistName);
    }

    @GetMapping("artists/{artistName}/albums")
    public List<Album> getAlbumsByArtistName(@PathVariable String artistName) {
        return artistService.getAlbumsByArtistName(artistName);
    }

    @GetMapping("artists")
    public List<Artist> getArtists() {
        return artistService.getArtists();
    }

    @PostMapping("artists")
    public ResponseArtistDTO saveArtist(@RequestBody ResponseArtistDTO responseArtistDto) {
        return artistService.addArtist(responseArtistDto.artistName());
    }

    @PutMapping("artists")
    public Artist updateArtist(@RequestBody Artist artist) {
        return artistService.updateArtist(artist.getId(), artist);
    }

    @DeleteMapping("artists/{id}")
    public Artist deleteArtist(@PathVariable Long id) {
        return artistService.deleteArtist(id);
    }
}

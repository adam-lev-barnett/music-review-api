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
@RequestMapping("artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/name/{artistName}")
    public Artist getArtistByName(@PathVariable String artistName) {
        return artistService.getArtistByName(artistName);
    }

    @GetMapping("/name/{artistName}/albums")
    public List<Album> getAlbumsByArtistName(@PathVariable String artistName) {
        return artistService.getAlbumsByArtistName(artistName);
    }

    @GetMapping
    public List<Artist> getArtists() {
        return artistService.getArtists();
    }

    @PostMapping
    public ResponseArtistDTO saveArtist(@RequestBody ResponseArtistDTO responseArtistDto) {
        return artistService.addArtist(responseArtistDto.artistName());
    }

    @PutMapping
    public Artist updateArtist(@RequestBody Artist artist) {
        return artistService.updateArtist(artist.getId(), artist);
    }

    @DeleteMapping("{id}")
    public Artist deleteArtist(@PathVariable Long id) {
        return artistService.deleteArtist(id);
    }
}

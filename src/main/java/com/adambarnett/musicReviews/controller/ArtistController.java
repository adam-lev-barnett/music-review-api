package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("artists/{artistName}")
    public Artist getArtistByName(@PathVariable String artistName) {
        return artistService.getArtistByName(artistName);
    }

    @GetMapping("artists/{artistName}/albums")
    public List<Album> getAlbumsByArtistName(@PathVariable String artistName) {
        return artistService.getAlbumsByArtistName(artistName);
    }

    @PostMapping("artists")
    public Artist saveArtist(@RequestBody Artist artist) {
        artistService.addArtist(artist.getArtistName());
        return artist;
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

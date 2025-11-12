package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(final AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("album/artist/{name}")
    public List<Album> getAlbumsByArtist(@PathVariable("name") String artistName) {
        return albumService.findByString(artistName, "artistName");
    }

    @GetMapping("album/name/{name}")
    public List<Album> getAlbumByAlbumName(@PathVariable("name") String albumName) {
        return albumService.findByString(albumName, "name");
    }

    @GetMapping("album/year/{releaseYear}")
    public List<Album> getAlbumsByReleaseYear(@PathVariable("releaseYear") Integer releaseYear) {
        return albumService.findByReleaseYear(releaseYear);
    }



}

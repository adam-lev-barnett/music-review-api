package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.service.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(final AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("albums/artist/{artistName}")
    public List<Album> getAlbumsByArtist(@PathVariable("artistName") String artistName) {
        return albumService.findByArtistName(artistName);
    }

    @GetMapping("albums/name/{albumName}")
    public Album getAlbumByAlbumName(@PathVariable("albumName") String albumName) {
        return albumService.getAlbumByAlbumName(albumName);
    }

    @GetMapping("albums/year/{releaseYear}")
    public List<Album> getAlbumsByReleaseYear(@PathVariable("releaseYear") Integer releaseYear) {
        return albumService.findByReleaseYear(releaseYear);
    }

    @PostMapping("albums")
    public Album addAlbum(@RequestBody Album album) throws InvalidArgumentException {
        return albumService.addAlbum(album);
    }

    @PutMapping("albums/{id}")
    public Album updateAlbum(@PathVariable Long id, @RequestBody Album album) {
        return albumService.updateAlbum(id, album);
    }

    @DeleteMapping("albums/{id}")
    public Album deleteAlbum(@PathVariable Long id) {
        return albumService.deleteAlbum(id);
    }

}

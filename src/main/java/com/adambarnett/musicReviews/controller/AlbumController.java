package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("albums/artist/{artistName}")
    public List<Album> getAlbumsByArtist(@PathVariable("artistName") String artistName) {
        return albumService.findByArtistName(artistName);
    }

    @GetMapping("albums/{albumName}/{artistName}")
    public Album getAlbumByAlbumNameAndArtistName(@PathVariable("albumName") String albumName, @PathVariable("artistName") String artistName) {
        return albumService.findByAlbumNameAndArtistName(albumName, artistName);
    }

    @GetMapping("albums/year/{releaseYear}")
    public List<Album> getAlbumsByReleaseYear(@PathVariable("releaseYear") Integer releaseYear) {
        return albumService.findByReleaseYear(releaseYear);
    }

    @GetMapping("albums/sort/{sortBy}")
    public List<Album> sortAllAlbums(@PathVariable("sortBy") String sortBy) {
        return albumService.sortAll(sortBy);
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

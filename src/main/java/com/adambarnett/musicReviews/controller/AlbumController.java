package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.dtos.AlbumDTO;
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
    public AlbumDTO getAlbumByAlbumNameAndArtistName(@PathVariable("albumName") String albumName, @PathVariable("artistName") String artistName) {
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
    public AlbumDTO addAlbum(@RequestBody AlbumDTO albumDTO) throws InvalidArgumentException {
        return albumService.addAlbum(albumDTO);
    }

    @PutMapping("albums/{id}")
    public AlbumDTO updateAlbum(@PathVariable Long id, @RequestBody AlbumDTO albumDTO) {
        return albumService.updateAlbum(albumDTO);
    }

    @DeleteMapping("albums/{id}")
    public AlbumDTO deleteAlbum(@PathVariable Long id) {
        return albumService.deleteAlbum(id);
    }

}

package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.dtos.albumdata.RequestAlbumDTO;
import com.adambarnett.musicReviews.dtos.albumdata.ResponseAlbumDTO;
import com.adambarnett.musicReviews.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/artist/{artistName}")
    public List<ResponseAlbumDTO> getAlbumsByArtist(@PathVariable("artistName") String artistName) {
        return albumService.findByArtistName(artistName);
    }

    @GetMapping("name/{albumName}/artist/{artistName}")
    public ResponseAlbumDTO getAlbumByAlbumNameAndArtistName(@PathVariable("albumName") String albumName, @PathVariable("artistName") String artistName) {
        return albumService.findByAlbumNameAndArtistName(albumName, artistName);
    }

    @GetMapping("/year/{releaseYear}")
    public List<ResponseAlbumDTO> getAlbumsByReleaseYear(@PathVariable("releaseYear") Integer releaseYear) {
        return albumService.findByReleaseYear(releaseYear);
    }

    @GetMapping("/sort/{sortBy}")
    public List<ResponseAlbumDTO> sortAllAlbums(@PathVariable("sortBy") String sortBy) {
        return albumService.sortAll(sortBy);
    }

    @GetMapping("/name/{albumName}")
    public ResponseAlbumDTO getAlbumByAlbumName(@PathVariable("albumName") String albumName) {
        return albumService.getAlbumByAlbumName(albumName);
    }

    @PostMapping
    public ResponseAlbumDTO addAlbum(@RequestBody RequestAlbumDTO requestAlbumDTO) throws InvalidArgumentException {
        return albumService.addAlbum(requestAlbumDTO);
    }

    @PutMapping("/{id}")
    public ResponseAlbumDTO updateAlbum(@PathVariable Long id, @RequestBody RequestAlbumDTO toUpdate) {
        return albumService.updateAlbum(id, toUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseAlbumDTO deleteAlbum(@PathVariable Long id) {
        return albumService.deleteAlbum(id);
    }

}

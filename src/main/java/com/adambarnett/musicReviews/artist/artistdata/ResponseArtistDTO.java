package com.adambarnett.musicReviews.artist.artistdata;

import com.adambarnett.musicReviews.artist.Artist;
import com.adambarnett.musicReviews.album.albumdata.ResponseAlbumDTO;

import java.util.List;

public record ResponseArtistDTO(
        Long id,
        String artistName,
        List<ResponseAlbumDTO> albumNames) {

    public ResponseArtistDTO(Artist artist) {
        // DTO should contain list of
        this(artist.getId(), artist.getArtistName(), artist.getAlbums().stream().map(ResponseAlbumDTO::new).toList());
    }
}

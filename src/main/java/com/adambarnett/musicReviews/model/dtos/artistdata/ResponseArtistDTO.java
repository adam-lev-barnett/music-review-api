package com.adambarnett.musicReviews.model.dtos.artistdata;

import com.adambarnett.musicReviews.model.Artist;
import com.adambarnett.musicReviews.model.dtos.albumdata.ResponseAlbumDTO;

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

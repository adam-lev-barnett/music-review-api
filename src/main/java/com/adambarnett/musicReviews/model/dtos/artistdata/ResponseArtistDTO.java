package com.adambarnett.musicReviews.model.dtos.artistdata;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;

import java.util.List;

public record ResponseArtistDTO(
        Long id,
        String artistName,
        List<Album> albumNames) {

    public ResponseArtistDTO(Artist artist) {
        this(artist.getId(), artist.getArtistName(), artist.getAlbums());
    }
}

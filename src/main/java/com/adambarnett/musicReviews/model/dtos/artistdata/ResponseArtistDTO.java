package com.adambarnett.musicReviews.model.dtos.artistdata;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ResponseArtistDTO(String artistName, List<Album> albumNames) {

    public ResponseArtistDTO(Artist artist) {
        this(artist.getArtistName(), artist.getAlbums());
    }
}

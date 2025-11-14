package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ArtistDTO(@NotNull String artistName, List<Album> albumNames) {

    public ArtistDTO(Artist artist) {
        this(artist.getArtistName(), artist.getAlbums());
    }
}

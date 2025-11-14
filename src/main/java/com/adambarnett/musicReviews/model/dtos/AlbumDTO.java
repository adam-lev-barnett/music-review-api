package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Review;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AlbumDTO(@NotNull Long id,
                       @NotNull String title,
                       @NotNull String artist,
                       @NotNull Integer releaseYear) {

    public AlbumDTO(Album album) {
        this(album.getId(), album.getAlbumName(), album.getArtist().getArtistName(), album.getReleaseYear());
    }

}

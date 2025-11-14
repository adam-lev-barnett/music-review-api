package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import jakarta.validation.constraints.NotNull;

public record AlbumDTO(@NotNull Long id,
                       @NotNull String albumName,
                       @NotNull Artist artist,
                       @NotNull Integer releaseYear) {

    public AlbumDTO(Album album) {
        this(album.getId(), album.getAlbumName(), album.getArtist(), album.getReleaseYear());
    }

}

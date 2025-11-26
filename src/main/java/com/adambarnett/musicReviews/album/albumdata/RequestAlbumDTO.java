package com.adambarnett.musicReviews.album.albumdata;

import jakarta.validation.constraints.NotNull;

public record RequestAlbumDTO(
                              @NotNull String albumName,
                              @NotNull String artistName,
                              @NotNull Integer releaseYear) {
}

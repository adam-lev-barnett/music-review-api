package com.adambarnett.musicReviews.model.dtos.albumdata;

import jakarta.validation.constraints.NotNull;

public record RequestAlbumDTO(
                              @NotNull String albumName,
                              @NotNull String artistName,
                              @NotNull Integer releaseYear) {
}

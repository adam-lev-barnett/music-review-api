package com.adambarnett.musicReviews.model.dtos.artistdata;


import jakarta.validation.constraints.NotNull;

public record RequestArtistDTO(@NotNull String artistName) {
}

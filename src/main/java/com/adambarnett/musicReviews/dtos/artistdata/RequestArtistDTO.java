package com.adambarnett.musicReviews.dtos.artistdata;


import jakarta.validation.constraints.NotNull;

public record RequestArtistDTO(@NotNull String artistName) {
}

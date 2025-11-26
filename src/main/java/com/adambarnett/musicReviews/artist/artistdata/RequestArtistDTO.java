package com.adambarnett.musicReviews.artist.artistdata;


import jakarta.validation.constraints.NotNull;

public record RequestArtistDTO(@NotNull String artistName) {
}

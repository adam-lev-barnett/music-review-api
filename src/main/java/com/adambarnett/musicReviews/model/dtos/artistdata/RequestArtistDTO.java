package com.adambarnett.musicReviews.model.dtos.artistdata;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RequestArtistDTO(@NotNull String artistName) {
}

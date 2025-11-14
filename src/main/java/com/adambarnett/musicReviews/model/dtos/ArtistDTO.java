package com.adambarnett.musicReviews.model.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ArtistDTO(@NotNull String artistName,
                        List<String> albumNames) {
}

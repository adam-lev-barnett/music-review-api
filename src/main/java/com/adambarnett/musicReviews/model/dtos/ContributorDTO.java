package com.adambarnett.musicReviews.model.dtos;

import jakarta.validation.constraints.NotNull;

public record ContributorDTO(@NotNull String name,
                             String favoriteArist) {

}

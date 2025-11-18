package com.adambarnett.musicReviews.model.dtos.contributordata;

import jakarta.validation.constraints.NotNull;

public record RequestContributorDTO(
                                    @NotNull String username,
                                    String favoriteArtistName) {
}

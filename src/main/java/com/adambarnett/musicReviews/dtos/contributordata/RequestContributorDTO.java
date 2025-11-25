package com.adambarnett.musicReviews.dtos.contributordata;

import jakarta.validation.constraints.NotNull;

public record RequestContributorDTO(
                                    @NotNull String username,
                                    @NotNull String password,
                                    String favoriteArtistName
                                    ) {
}

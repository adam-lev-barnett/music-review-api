package com.adambarnett.musicReviews.dtos.contributordata;

public record RequestContributorDTO(
                                    String username,
                                    String password,
                                    String favoriteArtistName
                                    ) {
}

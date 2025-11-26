package com.adambarnett.musicReviews.contributor.contributordata;

public record RequestContributorDTO(
                                    String username,
                                    String password,
                                    String favoriteArtistName
                                    ) {
}

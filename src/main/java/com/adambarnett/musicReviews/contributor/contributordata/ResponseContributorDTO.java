package com.adambarnett.musicReviews.contributor.contributordata;

import com.adambarnett.musicReviews.contributor.Contributor;

public record ResponseContributorDTO(Long id,
                                     String username,
                                     String favoriteArtistName) {

    public ResponseContributorDTO(Contributor contributor) {
        this(contributor.getId(), contributor.getUsername(), contributor.getFavoriteArtist().getArtistName());
    }
}

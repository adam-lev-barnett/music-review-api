package com.adambarnett.musicReviews.model.dtos.contributordata;

import com.adambarnett.musicReviews.model.Contributor;

public record ResponseContributorDTO(Long id,
                                     String username,
                                     String favoriteArtistName) {

    public ResponseContributorDTO(Contributor contributor) {
        this(contributor.getId(), contributor.getUsername(), contributor.getFavoriteArtist().getArtistName());
    }
}

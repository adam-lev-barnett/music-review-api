package com.adambarnett.musicReviews.model.dtos.contributordata;

import com.adambarnett.musicReviews.model.Contributor;
import jakarta.validation.constraints.NotNull;

public record ResponseContributorDTO(@NotNull String userName) {

    public ResponseContributorDTO(Contributor contributor) {
        this(contributor.getUsername());
    }
}

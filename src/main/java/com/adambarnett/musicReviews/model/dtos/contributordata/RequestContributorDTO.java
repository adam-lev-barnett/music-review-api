package com.adambarnett.musicReviews.model.dtos.contributordata;

import com.adambarnett.musicReviews.model.Contributor;
import jakarta.validation.constraints.NotNull;

public record RequestContributorDTO(String userName) {

    public RequestContributorDTO(Contributor contributor) {
        this(contributor.getUsername());
    }
}

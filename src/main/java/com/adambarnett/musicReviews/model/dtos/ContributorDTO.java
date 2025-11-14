package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Contributor;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.User;

public record ContributorDTO(@NotNull String userName) {

    public ContributorDTO(Contributor contributor) {
        this(contributor.getUsername());
    }
}

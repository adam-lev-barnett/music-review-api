package com.adambarnett.musicReviews.review.reviewdata;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

public enum ReviewStatus {
    @OneToMany
    @Enumerated(EnumType.STRING)
    SUBMITTED,

    @OneToMany
    @Enumerated(EnumType.STRING)
    APPROVED,

    @OneToMany
    @Enumerated(EnumType.STRING)
    REJECTED
}

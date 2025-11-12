package com.adambarnett.musicReviews.model;

import com.adambarnett.musicReviews.enums.ReviewStatus;
import org.springframework.stereotype.Component;

@Component
public class AdminReviewAction {

    private ReviewStatus status;

    public boolean isReviewAccepted() {
        return status == ReviewStatus.APPROVED;
    }

    public void setReviewAccepted(ReviewStatus status) {
        this.status = status;
    }
}

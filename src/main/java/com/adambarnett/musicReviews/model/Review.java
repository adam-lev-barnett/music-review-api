package com.adambarnett.musicReviews.model;

import com.adambarnett.musicReviews.enums.ReviewStatus;
import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


// A review is submitted by a contributor for a specific album
// An album can access its reviews through this table

@Entity
@Table(name="REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name="ARTIST_ID")
    @Getter @Setter private Artist artist;

    @NotNull
    @ManyToOne()
    @JoinColumn(name="ALBUM_ID")
    @Getter @Setter private Album album;

    @NotNull
    @Column(name="SCORE")
    @Getter private Integer score;

    @Column(name="COMMENTS")
    @Getter @Setter private String comments;

    @NotNull
    @ManyToOne()
    @JoinColumn(name="CONTRIBUTOR_ID")
    @Getter @Setter private Contributor contributor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private ReviewStatus reviewStatus;

    public void setScore(Integer score) throws InvalidArgumentException {
        if (score <= 0 || score > 100) {
            throw new InvalidArgumentException("Score must be between 0 and 100");
        }
        this.score = score;
    }
}

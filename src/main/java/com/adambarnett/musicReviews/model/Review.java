package com.adambarnett.musicReviews.model;

import com.adambarnett.musicReviews.enums.ReviewStatus;
import jakarta.persistence.*;
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

    @ManyToOne()
    @JoinColumn(name="ARTIST_ID")
    @Getter @Setter private Artist artist;

    @ManyToOne()
    @JoinColumn(name="ALBUM_ID")
    @Getter @Setter private Album album;

    @Column(name="SCORE")
    @Getter @Setter private Integer score;

    @Column(name="COMMENTS")
    @Getter @Setter private String comments;

    @ManyToOne()
    @JoinColumn(name="CONTRIBUTOR_ID")
    @Getter @Setter private Contributor contributor;

    @Getter @Setter private ReviewStatus reviewStatus;

}

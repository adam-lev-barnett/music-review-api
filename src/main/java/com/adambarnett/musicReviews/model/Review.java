package com.adambarnett.musicReviews.model;

import com.adambarnett.musicReviews.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.InvalidParameterException;

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
    @Getter private Integer score;

    @Column(name="COMMENTS")
    @Getter @Setter private String comments;

    @ManyToOne()
    @JoinColumn(name="CONTRIBUTOR_ID")
    @Getter @Setter private Contributor contributor;

    @Getter @Setter private ReviewStatus reviewStatus;

    public void setScore(Integer score) {
        if (score <= 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        this.score = score;
    }

    public Album setAlbum(Album album) {
        if (album == null) {
            throw new InvalidParameterException("Album can't be null");
        }
        this.album = album;
        return album;
    }

}

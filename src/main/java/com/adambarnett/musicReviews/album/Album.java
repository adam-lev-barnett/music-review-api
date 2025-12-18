package com.adambarnett.musicReviews.album;

import com.adambarnett.musicReviews.artist.Artist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name="ALBUM")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @NotNull
    @Column(name="ALBUM_NAME")
    @Getter @Setter private String albumName;

    @NotNull
    @ManyToOne
    @JoinColumn(name="ARTIST_ID")
    @Getter @Setter private Artist artist;

    @NotNull
    @Column(name="RELEASE_YEAR")
    @Getter @Setter private Integer releaseYear;

    /** Pair used to calculate average score using calculateAverageScore() method. [0] is number of reviews; [1] is total score.
     * Getter mainly for updating the array with its first review/score */
    @Getter private int[] reviewNumberAndCount = {0, 0};

    @Column(name="AVERAGE_SCORE")
    @Getter private Integer averageScore = 0;


    //TODO add field for average album score over reviews
        // Do not include field for reviews because it would overwhelm the column
        // Instead, search the review repository by album name and average the scores


    public void updateReviewNumberAndCount(int newScore) {
        reviewNumberAndCount[0]++;
        reviewNumberAndCount[1] += newScore;
        this.updateAverageScore();
    }

    private void updateAverageScore() {
        this.averageScore = reviewNumberAndCount[1] / reviewNumberAndCount[0];
    }


    @Override
    public String toString() {
        return String.format("Album: %s\nRelease year: %d\n", albumName, releaseYear);
    }

    // Album name case shouldn't matter -- Already handled in artist's equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return  Objects.equals(albumName.toLowerCase(), album.albumName.toLowerCase()) && Objects.equals(releaseYear, album.releaseYear) && album.artist.equals(artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumName.toLowerCase(), releaseYear, artist);
    }
}

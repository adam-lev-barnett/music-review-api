package com.adambarnett.musicReviews.album;

import com.adambarnett.musicReviews.artist.Artist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    //TODO add field for average album score over reviews

    @Override
    public String toString() {
        return String.format("Album: %s\nRelease year: %d\n", albumName, releaseYear);
    }
}

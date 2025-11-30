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

    //TODO add field for average album score over reviews

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

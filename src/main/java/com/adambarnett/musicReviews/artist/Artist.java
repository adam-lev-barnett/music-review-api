package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.contributor.contributordata.ResponseContributorDTO;
import com.adambarnett.musicReviews.album.Album;
import com.adambarnett.musicReviews.contributor.Contributor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="ARTIST")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @NotNull
    @Column(name="ARTIST_NAME")
    @Getter @Setter(AccessLevel.PUBLIC) private String artistName;

    // Mapped by "artist" versus "artistName" because album refers to the artist object as a whole
    // Artist and all associated entities removed upon artist removal from database
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Getter private List<Album> albums = new ArrayList<>();

    //TODO add field for average score across albums
    @Column(name="AVERAGE_ALBUM_SCORE")
    @Getter private Integer averageAlbumScore = 0;

    public void addAlbum(Album album) throws InvalidArgumentException {
        if (album == null) {
            throw new InvalidArgumentException("Album cannot be null");
        }
        albums.add(album);
    }

    public void updateAverageAlbumScore() {
        int totalScore = 0;
        for (Album album : albums) {
            totalScore += album.getAverageScore();
        }
        this.averageAlbumScore = totalScore / albums.size();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist artist)) return false;
        if (artistName == null || artist.getArtistName() == null) return false;
        return artistName.equalsIgnoreCase(artist.getArtistName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(artistName.toLowerCase());
    }


}

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
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @JsonIgnore
    @Getter private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "favoriteArtist")
    @JsonIgnore
    private List<Contributor> favoritedBy = new ArrayList<>();

    //TODO add field for average score across albums

    public void addAlbum(Album album) throws InvalidArgumentException {
        if (album == null) {
            throw new InvalidArgumentException("Album cannot be null");
        }
        albums.add(album);
    }

    public List<ResponseContributorDTO> getFavoritedBy() {
        return favoritedBy.stream().map(ResponseContributorDTO::new).toList();
    }

    public void addFavoritedBy(Contributor contributor) throws InvalidArgumentException {
        if (contributor == null) {
            throw new InvalidArgumentException("Contributor cannot be null");
        }
        favoritedBy.add(contributor);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return artistName.equalsIgnoreCase(artist.artistName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(artistName);
    }


}

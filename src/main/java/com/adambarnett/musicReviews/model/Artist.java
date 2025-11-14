package com.adambarnett.musicReviews.model;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ARTIST")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private long id;

    @Column(name="ARTIST_NAME")
    @Getter @Setter(AccessLevel.PUBLIC) private String artistName;

    // Mapped by "artist" versus "artistName" because album refers to the artist object as a whole
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @Getter private List<Album> albums = new ArrayList<>();

    //TODO add field for average score across albums

    public void addAlbum(Album album) throws InvalidArgumentException {
        if (album == null) {
            throw new InvalidArgumentException("Album cannot be null");
        }
        albums.add(album);
    }

}

package com.adambarnett.musicReviews.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

}

package com.adambarnett.musicReviews.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="ARTIST")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private long id;

    @Column(name="ARTIST_NAME")
    @Getter @Setter(AccessLevel.PUBLIC) private String artistName;

    // field in Album for artist name is "artist" not "artistName"
    @OneToMany(mappedBy = "artist")
    @Getter @Setter private List<Album> albums;

}

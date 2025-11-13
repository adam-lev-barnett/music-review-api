package com.adambarnett.musicReviews.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ALBUM")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @Column(name="ALBUM_NAME")
    @Getter @Setter private String albumName;

    @ManyToOne
    @JoinColumn(name="ARTIST_ID")
    @Getter @Setter private Artist artist;

    @Column(name="RELEASE_YEAR")
    @Getter @Setter private Integer releaseYear;

    //TODO add field for average album score over reviews

}

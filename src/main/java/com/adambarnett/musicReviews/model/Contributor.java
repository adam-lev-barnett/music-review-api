package com.adambarnett.musicReviews.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name="CONTRIBUTORS")
public class Contributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    @Getter private Long id;

    @Column(name="USERNAME")
    @NonNull
    @Getter @Setter private String username;

    @Getter @Setter private String favoriteArtist;


}

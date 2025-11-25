package com.adambarnett.musicReviews.model;

import com.adambarnett.musicReviews.enums.ContributorRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="CONTRIBUTORS")
public class Contributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    @Getter private Long id;

    @Column(name="USERNAME")
    @NotNull
    @Getter @Setter private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ARTIST_ID")
    @Getter @Setter private Artist favoriteArtist;

    @Column(name="PASSWORD")
    @NotNull
    @Getter @Setter private String password;

    @Column(name="ROLE")
    @NotNull
    @Getter @Setter private ContributorRole role;

}

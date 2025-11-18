package com.adambarnett.musicReviews.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

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

}

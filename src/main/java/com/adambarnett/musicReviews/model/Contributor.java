package com.adambarnett.musicReviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name="REVIEW_ID")
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }



}

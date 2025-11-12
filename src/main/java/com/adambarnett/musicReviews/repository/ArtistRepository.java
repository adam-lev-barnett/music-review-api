package com.adambarnett.musicReviews.repository;

import com.adambarnett.musicReviews.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {


    boolean existsByArtistName(String artistName);

    Optional<Artist> findByArtistName(String artistName);
}

package com.adambarnett.musicReviews.contributor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Optional<Contributor> findByUsername(String name);
    List<Contributor> findByFavoriteArtist_ArtistName(String favoriteArtistName);

}

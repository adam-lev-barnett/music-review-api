package com.adambarnett.musicReviews.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtist_ArtistName(String artistName);

    List<Album> findByReleaseYear(Integer releaseYear);

    Optional<Album> findByAlbumName(String albumName);

    Optional<Album> findByAlbumNameAndArtist_ArtistName(String albumName, String artistName);

    }

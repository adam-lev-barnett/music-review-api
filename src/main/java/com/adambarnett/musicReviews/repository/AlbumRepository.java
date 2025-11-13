package com.adambarnett.musicReviews.repository;

import com.adambarnett.musicReviews.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtist_ArtistName(String artistName);

    List<Album> findByReleaseYear(Integer releaseYear);

    Optional<Album> findByAlbumName(String albumName);
    
    boolean existsByAlbumName(String albumName);

    Optional<Album> findByAlbumNameAndArtist_ArtistName(String albumName, String artistName);

    }

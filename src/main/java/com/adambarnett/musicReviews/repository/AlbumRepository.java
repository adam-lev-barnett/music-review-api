package com.adambarnett.musicReviews.repository;

import com.adambarnett.musicReviews.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<AlbumDTO> findByArtist_ArtistName(String artistName);

    List<AlbumDTO> findByReleaseYear(Integer releaseYear);

    Optional<AlbumDTO> findByAlbumName(String albumName);

    Optional<AlbumDTO> findByAlbumNameAndArtist_ArtistName(String albumName, String artistName);


    }

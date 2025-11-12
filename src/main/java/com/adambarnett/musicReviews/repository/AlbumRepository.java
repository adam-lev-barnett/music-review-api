package com.adambarnett.musicReviews.repository;

import com.adambarnett.musicReviews.model.Album;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtist_ArtistName(String artistName);

    List<Album> findByReleaseYear(Integer releaseYear);

    List<Album> findByAlbumName(String albumName);
    
    boolean existsByAlbumName(String albumName);
}

package com.adambarnett.musicReviews.dtos.albumdata;

import com.adambarnett.musicReviews.model.Album;

public record ResponseAlbumDTO(Long id,
                               String albumName,
                               String artistName,
                               Integer releaseYear) {

    public ResponseAlbumDTO(Album album) {
        this(album.getId(), album.getAlbumName(), album.getArtist().getArtistName(), album.getReleaseYear());
    }

}

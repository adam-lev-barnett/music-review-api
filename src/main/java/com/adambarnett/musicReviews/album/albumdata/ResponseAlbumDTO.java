package com.adambarnett.musicReviews.album.albumdata;

import com.adambarnett.musicReviews.album.Album;

public record ResponseAlbumDTO(Long id,
                               String albumName,
                               String artistName,
                               Integer releaseYear) {

    public ResponseAlbumDTO(Album album) {
        this(album.getId(), album.getAlbumName(), album.getArtist().getArtistName(), album.getReleaseYear());
    }

}

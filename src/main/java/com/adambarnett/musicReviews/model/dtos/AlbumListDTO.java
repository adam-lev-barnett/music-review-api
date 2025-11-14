package com.adambarnett.musicReviews.model.dtos;

import com.adambarnett.musicReviews.model.Album;
import com.adambarnett.musicReviews.model.Artist;

import java.util.List;

public record AlbumListDTO(List<Album> albumList) {
    public AlbumListDTO(Artist artist) {
        this(artist.getAlbums());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for  (Album album : albumList) {
            sb.append(album.toString()).append("\n");
        }
        return sb.toString();
    }
}

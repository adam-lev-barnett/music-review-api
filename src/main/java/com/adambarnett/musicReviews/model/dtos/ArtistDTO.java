package com.adambarnett.musicReviews.model.dtos;

import java.util.List;

public record ArtistDTO(String artistName, List<String> albumNames) {
}

package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.model.dtos.contributordata.RequestContributorDTO;
import com.adambarnett.musicReviews.model.dtos.contributordata.ResponseContributorDTO;
import com.adambarnett.musicReviews.service.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("contributors")
public class ContributorController {

    private final ContributorService contributorService;

    @GetMapping("/{username}")
    public ResponseContributorDTO getContributorByUsername(@PathVariable String username) throws ResponseStatusException {
        return contributorService.findByUsername(username);
    }

    @GetMapping
    public List<ResponseContributorDTO> getContributorsByFavoriteArtistName(@RequestParam String artistName) throws ResponseStatusException {
        return contributorService.findByFavoriteArtist_ArtistName(artistName);
    }

    @PostMapping
    public ResponseContributorDTO registerContributor(@RequestBody RequestContributorDTO contributorDto) throws ResponseStatusException, InvalidArgumentException {
        return contributorService.registerNewContributor(contributorDto.username(), contributorDto.favoriteArtistName());
    }

    @PutMapping("/{username}/favorite-artist/{artistName}")
    public ResponseContributorDTO updateFavoriteArtist(@PathVariable String username, @PathVariable String artistName) throws ResponseStatusException {
        return contributorService.updateFavoriteArtist(username, artistName);
    }


}

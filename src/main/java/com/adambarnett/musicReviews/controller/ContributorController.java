package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidUserException;
import com.adambarnett.musicReviews.model.dtos.contributordata.RequestContributorDTO;
import com.adambarnett.musicReviews.model.dtos.contributordata.ResponseContributorDTO;
import com.adambarnett.musicReviews.service.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("contributors")
public class ContributorController {

    private final ContributorService contributorService;

    @GetMapping("/{username}")
    public ResponseContributorDTO getContributorByUsername(@PathVariable String username) throws InvalidUserException {
        return contributorService.findByUsername(username);
    }

    @GetMapping("/favoriteArtist/{artistName}")
    public List<ResponseContributorDTO> getContributorsByFavoriteArtistName(@PathVariable String artistName) throws InvalidUserException {
        return contributorService.findByFavoriteArtist_ArtistName(artistName);
    }

    @PostMapping("/register")
    public ResponseContributorDTO registerContributor(@RequestBody RequestContributorDTO contributorDto) throws InvalidUserException {
        return contributorService.registerNewContributor(contributorDto.username(), contributorDto.favoriteArtistName());
    }

    @PutMapping("/{username}/favoriteArtist/{favoriteArtistName}")
    public ResponseContributorDTO updateFavoriteArtist(@PathVariable String username, @PathVariable String favoriteArtistName) throws InvalidUserException {
        return contributorService.updateFavoriteArtist(username, favoriteArtistName);
    }


}

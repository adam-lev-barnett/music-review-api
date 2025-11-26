package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.dtos.auth.RegisterRequestDTO;
import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.dtos.contributordata.RequestContributorDTO;
import com.adambarnett.musicReviews.dtos.contributordata.ResponseContributorDTO;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.service.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contributors")
public class ContributorController {

    private final ContributorService contributorService;

    @PostMapping("/register")
    public ResponseContributorDTO registerContributor(@RequestBody RegisterRequestDTO registerRequestDTO) throws InvalidArgumentException {
        return contributorService.registerNewContributor(registerRequestDTO);
    }

    @GetMapping("/me")
    public ResponseContributorDTO getMe(Authentication auth) {
        return contributorService.getSelf(auth);
    }

    @GetMapping("/{username}")
    public ResponseContributorDTO getContributorByUsername(@PathVariable String username) throws ResponseStatusException {
        return contributorService.findByUsername(username);
    }


    @GetMapping
    public List<ResponseContributorDTO> getContributorsByFavoriteArtistName(@RequestParam String artistName) throws ResponseStatusException {
        return contributorService.findByFavoriteArtist_ArtistName(artistName);
    }

    @PutMapping("/me/favorite-artist")
    public ResponseContributorDTO updateFavoriteArtist(Authentication auth, @RequestBody RequestContributorDTO contributorDTO) throws ResponseStatusException {
        return contributorService.updateFavoriteArtist(auth, contributorDTO);
    }

}

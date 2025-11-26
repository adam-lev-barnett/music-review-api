package com.adambarnett.musicReviews.contributor;

import com.adambarnett.musicReviews.auth.dto.RegisterRequestDTO;
import com.adambarnett.musicReviews.contributor.contributordata.ContributorRole;
import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import com.adambarnett.musicReviews.artist.Artist;
import com.adambarnett.musicReviews.contributor.contributordata.RequestContributorDTO;
import com.adambarnett.musicReviews.contributor.contributordata.ResponseContributorDTO;
import com.adambarnett.musicReviews.artist.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO decide on what to do when an artist doesn't exist anymore

@Service
@RequiredArgsConstructor
public class ContributorService {

    private final ContributorRepository contributorRepository;
    private final ArtistRepository artistRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseContributorDTO registerNewContributor(RegisterRequestDTO requestDTO) throws ResponseStatusException, InvalidArgumentException {
        Optional<Contributor> contributorOptional = contributorRepository.findByUsername(requestDTO.username());
        if (contributorOptional.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        Contributor newContributor = new Contributor();
        newContributor.setUsername(requestDTO.username());
        setUserPassword(requestDTO.password(), newContributor);
        Artist favoriteArtist = getOrCreateNewArtist(requestDTO.favoriteArtist());
        newContributor.setFavoriteArtist(favoriteArtist);
        newContributor.setRole(ContributorRole.ROLE_USER);
        System.out.println("Contributor successfully registered");
        Contributor registeredContributor = contributorRepository.save(newContributor);
        return new ResponseContributorDTO(registeredContributor);
    }

    // password encryption helper method for registerNewContributor()
    private void setUserPassword(String basicPassword, Contributor contributor) {
        if (basicPassword == null || basicPassword.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be empty");
        }
        String hashedPassword = passwordEncoder.encode(basicPassword);
        contributor.setPassword(hashedPassword);
    }

    public ResponseContributorDTO getSelf(Authentication auth) throws ResponseStatusException {
        String username = auth.getName();
        Contributor contributor = contributorRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributor not found"));
        return new ResponseContributorDTO(contributor);
    }

    public ResponseContributorDTO updateFavoriteArtist(Authentication auth, RequestContributorDTO contributorDTO) throws ResponseStatusException {
        String username = auth.getName();
        Optional<Contributor> contributorOptional = contributorRepository.findByUsername(username);
        if (contributorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update contributor; contributor does not exist");
        }
        Contributor updatedContributor = contributorOptional.get();
        updatedContributor.setFavoriteArtist(getOrCreateNewArtist(contributorDTO.favoriteArtistName()));
        System.out.println("Contributor artist successfully updated");
        return new ResponseContributorDTO(contributorRepository.save(updatedContributor));
    }

    public ResponseContributorDTO findByUsername(String username) throws ResponseStatusException {
        Contributor contributor =  contributorRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contributor not found"));
        return new ResponseContributorDTO(contributor);
    }

    public List<ResponseContributorDTO> findByFavoriteArtist_ArtistName(String artistName) {
        List<ResponseContributorDTO> responseContributorDTOList = new ArrayList<>();
        List<Contributor> contributorsList = contributorRepository.findByFavoriteArtist_ArtistName(artistName);
        if (contributorsList.isEmpty()) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found"); }
        for (Contributor contributor : contributorsList) {
            responseContributorDTOList.add(new ResponseContributorDTO(contributor));
        }
        return responseContributorDTOList;
    }

    /** Returns artist from artistRepository or creates new artist to return*/
    public Artist getOrCreateNewArtist(String favoriteArtistName) {
        Artist favoriteArtist = artistRepository.findByArtistName(favoriteArtistName)
                .orElseGet( () -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(favoriteArtistName);
                    return artistRepository.save(newArtist);
                });
        return favoriteArtist;
    }




}

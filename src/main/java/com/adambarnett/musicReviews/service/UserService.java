package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.exception.InvalidUserException;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.model.dtos.ContributorDTO;
import com.adambarnett.musicReviews.repository.ContributorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final ContributorRepository contributorRepository;

    public UserService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    public ContributorDTO registerNewContributor(String username, String favoriteArist) throws InvalidUserException {
        Optional<Contributor> contributorOptional = contributorRepository.findByUsername(username);
        if (contributorOptional.isPresent()) throw new InvalidUserException("Cannot register new contributor; contributor already exists");
        Contributor newContributor = new Contributor();
        System.out.println("Contributor successfully registered");
        contributorRepository.save(newContributor);
        return new ContributorDTO(username, favoriteArist);
    }

    public ContributorDTO updateContributorFavoriteArtist(String username, String favoriteArtist) throws InvalidUserException {
        Optional<Contributor> contributorOptional = contributorRepository.findByUsername(username);
        if (contributorOptional.isEmpty()) {
            throw new InvalidUserException("Cannot update contributor; contributor does not exist");
        }
        Contributor updatedContributor = contributorOptional.get();
        updatedContributor.setFavoriteArtist(favoriteArtist);
        System.out.println("Contributor artist successfully updated");
        return new ContributorDTO(username, favoriteArtist);
    }

}

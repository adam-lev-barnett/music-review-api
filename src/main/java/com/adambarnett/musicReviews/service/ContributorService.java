package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.exception.InvalidUserException;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.repository.ContributorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContributorService {

    private final ContributorRepository contributorRepository;

    public Contributor registerNewContributor(String username) throws InvalidUserException {
        Optional<Contributor> contributorOptional = contributorRepository.findByUsername(username);
        if (contributorOptional.isPresent()) throw new InvalidUserException("Cannot register new contributor; contributor already exists");
        Contributor newContributor = new Contributor();
        System.out.println("Contributor successfully registered");
        return contributorRepository.save(newContributor);
    }

    public Contributor updateContributorFavoriteArtist(String username, String favoriteArtist) throws InvalidUserException {
        Optional<Contributor> contributorOptional = contributorRepository.findByUsername(username);
        if (contributorOptional.isEmpty()) {
            throw new InvalidUserException("Cannot update contributor; contributor does not exist");
        }
        Contributor updatedContributor = contributorOptional.get();
        updatedContributor.setFavoriteArtist(favoriteArtist);
        System.out.println("Contributor artist successfully updated");
        return contributorRepository.save(updatedContributor);
    }

}

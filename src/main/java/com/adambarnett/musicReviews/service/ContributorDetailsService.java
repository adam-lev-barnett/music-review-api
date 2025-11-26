package com.adambarnett.musicReviews.service;

import com.adambarnett.musicReviews.repository.ContributorRepository;
import com.adambarnett.musicReviews.utility.ContributorUserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContributorDetailsService implements UserDetailsService {
    private final ContributorRepository contributorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ContributorUserWrapper(contributorRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("User not found with username: " + username)));
    }
}

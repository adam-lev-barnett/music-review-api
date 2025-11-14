package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.exception.InvalidUserException;
import com.adambarnett.musicReviews.model.Contributor;
import com.adambarnett.musicReviews.service.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;

    @PostMapping("contributors/register/{newUser}")
    public Contributor registerContributor(@PathVariable("newUser") String newUser) throws InvalidUserException {
        contributorService.registerNewContributor(newUser);
    }




}

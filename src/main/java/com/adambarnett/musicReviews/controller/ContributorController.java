package com.adambarnett.musicReviews.controller;

import com.adambarnett.musicReviews.service.ContributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContributorController {

    private final ContributorService contributorService;


}

package com.adambarnett.musicReviews.repository;

import com.adambarnett.musicReviews.model.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Optional<Contributor> findByUsername(String name);
}

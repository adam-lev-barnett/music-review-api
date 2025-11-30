package com.adambarnett.musicReviews.artist;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArtistRepositoryTest {

    private final Artist testArtist = new Artist();

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    void setUp() {
        this.testArtist.setArtistName("Test Artist");
        artistRepository.save(testArtist);
    }

    // Clear database after each test
    @AfterEach
    void tearDown() {
        artistRepository.deleteAll();
    }

    //~~~~ findById ~~~~~

    @Test
    void testFindExistingIdSucceeds() {
        assertEquals(artistRepository.findById(1L).orElseGet(Artist::new), testArtist);
    }

    @Test
    void testFindNonExistentArtistByReturnsEmptyOptional() {
        Optional<Artist> result = artistRepository.findById(100L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSuccessfulArtistNameSearch() {
        Optional<Artist> result = artistRepository.findByArtistName("Test Artist");
        //! Artist should always exist in test
        Artist artist = result.get();
        assertEquals(artist, testArtist);
    }

    @Test
    void testFindNonExistentArtistByNameReturnsEmptyOptional() {
        Optional<Artist> result = artistRepository.findByArtistName("I don't exist");
        assertTrue(result.isEmpty());
    }
}

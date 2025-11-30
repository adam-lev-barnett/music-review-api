package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.album.Album;
import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistTest {
    Artist testArtist;

    @BeforeEach
    void setUp() {
        this.testArtist = new Artist();
        testArtist.setArtistName("Test Artist");
    }

    @Test
    void testAddNewAlbumSucceeds() throws InvalidArgumentException {
        Album testAlbum = new Album();
        testAlbum.setAlbumName("Test Album");
        testAlbum.setArtist(testArtist);
        testAlbum.setReleaseYear(1994);
        testArtist.addAlbum(testAlbum);
        assertTrue(testArtist.getAlbums().contains(testAlbum));
    }

    @Test
    void testAddNullAlbumThrowsException() {
        assertThrows(InvalidArgumentException.class, () -> testArtist.addAlbum(null));
    }

    @Test
    void testEqualWhenArtistNameIsSameCase() {
        Artist testArtist2 = new Artist();
        testArtist2.setArtistName("Test Artist");
        assertEquals(testArtist, testArtist2);
    }

    @Test
    void testEqualWhenDifferentCaseArtistName() {
        Artist testArtist2 = new Artist();
        testArtist2.setArtistName("Test artist");
        assertEquals(testArtist, testArtist2);
    }

    @Test
    void testHashCode() {
        Artist testArtist2 = new Artist();
        testArtist2.setArtistName("Test Artist");
        assertEquals(testArtist.hashCode(), testArtist2.hashCode());
    }
}

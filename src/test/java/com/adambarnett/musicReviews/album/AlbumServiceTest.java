package com.adambarnett.musicReviews.album;

import com.adambarnett.musicReviews.album.albumdata.RequestAlbumDTO;
import com.adambarnett.musicReviews.album.albumdata.ResponseAlbumDTO;
import com.adambarnett.musicReviews.artist.Artist;
import com.adambarnett.musicReviews.artist.ArtistRepository;
import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {
    Album testAlbum;
    Artist testArtist;
    RequestAlbumDTO testRequestAlbumDTO;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private AlbumService albumService;

    @BeforeEach
    public void setUp() {

        // Instantiate artist first to assign to album
        this.testArtist = new Artist();
        this.testArtist.setArtistName("Test Artist");

        // Instantiate album
        this.testAlbum = new Album();
        this.testAlbum.setAlbumName("Test Album");
        this.testAlbum.setArtist(testArtist);
        this.testAlbum.setReleaseYear(1999);

        // Create request DTO to add new albums to mock repository
        this.testRequestAlbumDTO = new RequestAlbumDTO(testAlbum.getAlbumName(), testAlbum.getArtist().getArtistName(), testAlbum.getReleaseYear());

    }

    //~~~~~~~~~~ Add album tests ~~~~~~~~~~//

    @Test
    void testAddNewAlbumWithExistingArtistSucceeds() throws InvalidArgumentException {

        // artistRepository.findByArtistName() is invoked to check if artist already exists
        when(this.artistRepository.findByArtistName(testArtist.getArtistName()))
                .thenReturn(Optional.of(testArtist));

        when(this.albumRepository.save(any(Album.class)))
                .thenReturn(testAlbum);

        ResponseAlbumDTO responseAlbumDTO = albumService.addAlbum(testRequestAlbumDTO);

        // Check all fields
        assertEquals(responseAlbumDTO.albumName(), testAlbum.getAlbumName());
        assertEquals(responseAlbumDTO.artistName(), testAlbum.getArtist().getArtistName());
        assertEquals(responseAlbumDTO.releaseYear(), testAlbum.getReleaseYear());
    }

    /** Ensures that adding a new album with artist not already in repository.
     1. Looks up the new artist
     2. Doesn't find it
     3. Saves the new artist to artist repository
     4. Assigns it to the album */

    @Test
    void testAddNewAlbumWithNewArtistCreatesNewArtist() throws InvalidArgumentException {

        Artist newArtist = new Artist();
        newArtist.setArtistName("New Artist");

        // artistRepository.findByArtistName() is invoked to check if artist already exists
        when(this.artistRepository.findByArtistName(testRequestAlbumDTO.artistName()))
                .thenReturn(Optional.empty());

        // Creates the artist
        when(this.artistRepository.save(any(Artist.class)))
                .thenReturn(newArtist);

        when(this.albumRepository.save(any(Album.class)))
                .thenReturn(testAlbum);

        ResponseAlbumDTO responseAlbumDTO =  albumService.addAlbum(testRequestAlbumDTO);

        // Check all
        assertEquals(responseAlbumDTO.albumName(), testAlbum.getAlbumName());
        assertEquals(responseAlbumDTO.artistName(), testAlbum.getArtist().getArtistName());
        assertEquals(responseAlbumDTO.releaseYear(), testAlbum.getReleaseYear());
    }

    @Test
    void testAddNullAlbumThrowsException() {
        assertThrows(InvalidArgumentException.class, () -> albumService.addAlbum(null));
    }

    @Test
    void testAddAlbumWithNegativeReleaseYearThrowsException() {
        RequestAlbumDTO requestNegativeYearAlbumDTO = new RequestAlbumDTO("Album name", "Artist name", -1);
        assertThrows(InvalidArgumentException.class, () -> albumService.addAlbum(requestNegativeYearAlbumDTO));
    }

    /** Ensures that updating all fields of an existing album goes through the following steps:
     1. Looks up the album by ID
     2. Finds it
     3. Looks up artist
     4. Doesn't find it
     5. Saves new artist to artist repository
     4. Assigns it to the album */

    @Test
    void testUpdateAllExistingAlbumFieldsSucceeds() {

        // Create new artist to update album
        Artist newArtist = new Artist();
        newArtist.setArtistName("New artist");

        RequestAlbumDTO updatingRequestAlbumDTO = new RequestAlbumDTO("New album name", newArtist.getArtistName(), 1998);

        when(this.albumRepository.findById(1L)).thenReturn(Optional.of(testAlbum));

        when(this.artistRepository.findByArtistName(updatingRequestAlbumDTO.artistName()))
            .thenReturn(Optional.empty());

        when(artistRepository.save(any(Artist.class)))
            .thenReturn(newArtist);

        when(this.albumRepository.save(any(Album.class)))
            .thenReturn(testAlbum);

        ResponseAlbumDTO updatedAlbumDTO = albumService.updateAlbum(1L, updatingRequestAlbumDTO);

        assertEquals(updatedAlbumDTO.albumName(), testAlbum.getAlbumName());
        assertEquals(updatedAlbumDTO.artistName(), testAlbum.getArtist().getArtistName());
        assertEquals(updatedAlbumDTO.releaseYear(), testAlbum.getReleaseYear());
    }

    @Test
    void testUpdateNonexistentAlbumThrowsException() {
        when(this.albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> albumService.updateAlbum(1L, testRequestAlbumDTO));

    }

    @Test
    void deleteAlbum() {
    }

    @Test
    void findByArtistName() {
    }

    @Test
    void findByAlbumNameAndArtistName() {
    }

    @Test
    void findByReleaseYear() {
    }

    @Test
    void sortAll() {
    }

    @Test
    void getAlbumByAlbumName() {
    }
}

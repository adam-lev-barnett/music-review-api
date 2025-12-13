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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    void testDeleteAlbumSucceeds() {

        when(this.albumRepository.findById(1L)).thenReturn(Optional.of(testAlbum));

        assertDoesNotThrow(() -> albumService.deleteAlbum(1L));

        verify(albumRepository).delete(testAlbum);
    }

    @Test
    void testFindByArtistNameSucceeds() {

        // Instantiate new album with existing artist
        Album testAlbum2 = new Album();
        testAlbum2.setAlbumName("Test Album 2");
        testAlbum2.setArtist(testArtist);
        testAlbum2.setReleaseYear(2002);

        when(artistRepository.findByArtistName(testArtist.getArtistName()))
                .thenReturn(Optional.of(testArtist));

        when(albumRepository.findByArtist_ArtistName(testArtist.getArtistName()))
                .thenReturn(List.of(testAlbum2, testAlbum));

        List<ResponseAlbumDTO> responseAlbumDTOs = albumService.findByArtistName(testArtist.getArtistName());

        assertEquals(responseAlbumDTOs.get(0).albumName(), testAlbum.getAlbumName());
        assertEquals(responseAlbumDTOs.get(1).albumName(), testAlbum2.getAlbumName());

        assertEquals(responseAlbumDTOs.get(0).artistName(), testAlbum.getArtist().getArtistName());
        assertEquals(responseAlbumDTOs.get(1).artistName(), testAlbum2.getArtist().getArtistName());

        // Ensure the artist names are the same between them
        assertEquals(responseAlbumDTOs.get(0).artistName(), responseAlbumDTOs.get(1).artistName());

        assertEquals(responseAlbumDTOs.get(0).releaseYear(), testAlbum.getReleaseYear());
        assertEquals(responseAlbumDTOs.get(1).releaseYear(), testAlbum2.getReleaseYear());
    }

    @Test
    void testFindByArtistNameThrowsExceptionWhenNotFound() {
        when(this.artistRepository.findByArtistName(testArtist.getArtistName()))
            .thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> albumService.findByArtistName(testArtist.getArtistName()));
    }

    @Test
    void testFindByArtistNameThrowsExceptionWhenFieldEmpty() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByArtistName(""));
    }

    @Test
    void testFindByArtistNameThrowsExceptionWhenFieldNull() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByArtistName(null));
    }

    @Test
    void testFindByAlbumNameAndArtistNameSucceeds() {
        when(this.artistRepository.findByArtistName(testArtist.getArtistName()))
                .thenReturn(Optional.of(testArtist));

        when(this.albumRepository.findByAlbumNameAndArtist_ArtistName(testAlbum.getAlbumName(), testArtist.getArtistName()))
                .thenReturn(Optional.of(testAlbum));

        ResponseAlbumDTO responseAlbumDTO = albumService.findByAlbumNameAndArtistName(testAlbum.getAlbumName(), testArtist.getArtistName());

        assertEquals(testAlbum.getAlbumName(), responseAlbumDTO.albumName());
        assertEquals(testAlbum.getArtist().getArtistName(), responseAlbumDTO.artistName());
        assertEquals(testAlbum.getReleaseYear(), responseAlbumDTO.releaseYear());
    }

    @Test
    void testFindByNonexistentArtistAndAlbumNameThrowsException() {
        when(this.artistRepository.findByArtistName(testArtist.getArtistName()))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> albumService.findByAlbumNameAndArtistName(testAlbum.getAlbumName(), testArtist.getArtistName()));
    }

    @Test
    void testFindByNonexistentAlbumButExistingArtistThrowsException() {

        // Artist is found
        when(this.artistRepository.findByArtistName(testArtist.getArtistName()))
                .thenReturn(Optional.of(testArtist));

        // Album doesn't exist
        when(this.albumRepository.findByAlbumNameAndArtist_ArtistName(testAlbum.getAlbumName(), testArtist.getArtistName()))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> albumService.findByAlbumNameAndArtistName(testAlbum.getAlbumName(), testArtist.getArtistName()));
    }

    @Test
    void testFindByNullArtistNameAndFilledAlbumNameThrowsException() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByAlbumNameAndArtistName(null, testAlbum.getAlbumName()));
    }

    @Test
    void testFindByEmptyArtistNameAndFilledAlbumNameThrowsException() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByAlbumNameAndArtistName("", testAlbum.getAlbumName()));
    }

    @Test
    void testFindByFilledArtistNameAndNullAlbumNameThrowsException() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByAlbumNameAndArtistName(testArtist.getArtistName(), null));
    }

    @Test
    void testFindByFilledArtistNameAndEmptyAlbumNameThrowsException() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByAlbumNameAndArtistName(testArtist.getArtistName(), ""));
    }

    @Test
    void testFindByListedReleaseYearReturnsAllCorrectAlbums() {

        Artist testArtist2 = new Artist();
        testArtist2.setArtistName("Test artist 2");

        Album testAlbumSameYear = new Album();
        testAlbumSameYear.setArtist(testArtist2);
        testAlbumSameYear.setAlbumName("Another 1999 album");
        testAlbumSameYear.setReleaseYear(testAlbum.getReleaseYear());

        Album shouldNotBeIncluded = new Album();
        shouldNotBeIncluded.setArtist(testArtist2);
        shouldNotBeIncluded.setAlbumName("Album from a different year");
        shouldNotBeIncluded.setReleaseYear(testAlbum.getReleaseYear());

        when(this.albumRepository.findByReleaseYear(1999))
                .thenReturn(List.of(testAlbum, testAlbumSameYear));

        List<ResponseAlbumDTO> albumsFromSameReleaseYear = albumService.findByReleaseYear(1999);

        // Ensures the album from a different year isn't included
        assertEquals(2, albumsFromSameReleaseYear.size());

        assertEquals(albumsFromSameReleaseYear.get(0).artistName(), testAlbum.getArtist().getArtistName());
        assertEquals(albumsFromSameReleaseYear.get(0).albumName(), testAlbum.getAlbumName());
        assertEquals(albumsFromSameReleaseYear.get(0).releaseYear(), testAlbum.getReleaseYear());

        assertEquals(albumsFromSameReleaseYear.get(1).artistName(), testAlbumSameYear.getArtist().getArtistName());
        assertEquals(albumsFromSameReleaseYear.get(1).albumName(), testAlbumSameYear.getAlbumName());
        assertEquals(albumsFromSameReleaseYear.get(1).releaseYear(), testAlbumSameYear.getReleaseYear());
    }

    @Test
    void testFindByUnlistedReleaseYearReturnsEmptyList() {
        when(this.albumRepository.findByReleaseYear(500))
                .thenReturn(List.of());

        assertTrue(albumService.findByReleaseYear(500).isEmpty());
    }

    @Test
    void testFindByNegativeReleaseYearThrowsException() {
        assertThrows(ResponseStatusException.class, () -> albumService.findByReleaseYear(-500));
    }

    @Test
    void testSortAll() {
    }

    @Test
    void testGetAlbumsByAlbumName() {
    }
}

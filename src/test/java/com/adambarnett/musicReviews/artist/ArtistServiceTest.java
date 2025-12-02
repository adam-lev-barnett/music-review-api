package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.artist.artistdata.RequestArtistDTO;
import com.adambarnett.musicReviews.artist.artistdata.ResponseArtistDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    //~~~~~~~ getArtistByName() tests~~~~~~~~~~~
    @Test
    void testGetExistingArtistByName() {
        Artist testArtist = new Artist();
        testArtist.setArtistName("Test Artist");

        when(artistRepository.findByArtistName("Test Artist"))
                .thenReturn(Optional.of(testArtist));

        ResponseArtistDTO responseArtistDTO = artistService.getArtistByName("Test Artist");
        assertEquals("Test Artist", responseArtistDTO.artistName());
    }

    @Test
    void testGetNonExistingArtistThrowsNotFoundException() {
        when(artistRepository.findByArtistName(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> artistService.getArtistByName("Non existing Artist"));
    }

    @Test
    void testGetNullArtistThrowsNotFoundException() {
        assertThrows(ResponseStatusException.class, () -> artistService.getArtistByName(null));
    }

    //~~~~~~~ addArtist() tests~~~~~~~~~~~
    @Test
    void testAddNewArtistSavesInRepository() {

        Artist testArtist2 = new Artist();
        testArtist2.setArtistName("Test Artist");

        RequestArtistDTO requestArtistDTO = new RequestArtistDTO("Test Artist");

        when(artistRepository.save(any(Artist.class)))
                .thenReturn(testArtist2);

        ResponseArtistDTO responseArtistDTO = artistService.addArtist(requestArtistDTO);
        assertEquals(requestArtistDTO.artistName(),  responseArtistDTO.artistName());
    }

    @Test
    void testAddExistingArtistThrowsResponseStatusException() {
        Artist testArtist = new Artist();
        testArtist.setArtistName("Test Artist");

        RequestArtistDTO requestDTO = new RequestArtistDTO("Test Artist");

        // Technically can't save artist, so saving "existing artist" can be omitted
        when(artistRepository.findByArtistName("Test Artist"))
                .thenReturn(Optional.of(testArtist));

        assertThrows(ResponseStatusException.class, () -> artistService.addArtist(requestDTO));
    }

    @Test
    void testAddNullArtistThrowsResponseStatusException() {
        assertThrows(ResponseStatusException.class, () -> artistService.addArtist(null));
    }

    @Test
    void testAddEmptyStringArtistThrows() {
        assertThrows(ResponseStatusException.class, () -> artistService.addArtist(new RequestArtistDTO("")));
    }

    //~~~~~~~ updateArtist() tests~~~~~~~~~~~

    @Test
    void testUpdateExistingArtistSucceeds() {
        Artist testArtist = new Artist();
        testArtist.setArtistName("Test Artist");

        // Pretend testArtist is already saved in the database

        // Allows for first argument of updateArtist (id field) because id isn't generated with stub
        when(artistRepository.findById(1L))
            .thenReturn(Optional.of(testArtist));

        // Ensures an artist is returned so it can be updated with updateArtist
        when(artistRepository.save(testArtist))
            .thenReturn(testArtist);

        RequestArtistDTO requestDTO = new RequestArtistDTO("Test Artist 2");

        // Should return the actual testArtist and modify it based on the RequestDTO
        artistService.updateArtist(1L, requestDTO);

        // Directly compare to the artist itself for direct testing of update
        assertEquals("Test Artist 2", testArtist.getArtistName());
    }

    @Test
    void testUpdateNonExistingArtistThrowsNotException() {
        assertThrows(ResponseStatusException.class, () -> artistService.updateArtist(1L, new RequestArtistDTO("")));
    }

    //~~~~~~~ GetArtistsBy... tests~~~~~~~~~~~

    @Test
    void testGetAllArtistsReturnsAllArtists() {

        // Populate repository with artists
        Artist testArtist1 = new Artist();
        Artist testArtist2 = new Artist();
        testArtist1.setArtistName("Test Artist 1");
        testArtist2.setArtistName("Test Artist 2");

        // Ensure that service correctly calls the mock repository
        when(artistRepository.findAll(Sort.by(Sort.Direction.ASC, "artistName"))).thenReturn(List.of(testArtist1, testArtist2));

        // Populate artistName array for comparison of getArtists() list
        String[] nameList1 = {testArtist1.getArtistName(),  testArtist2.getArtistName()};

        List<ResponseArtistDTO> artistList = artistService.getArtists();

        String[] nameList2 = {artistList.get(0).artistName(), artistList.get(1).artistName()};

        assertArrayEquals(nameList1, nameList2);
    }

    //~~~~~~~ GetArtistsBy... tests~~~~~~~~~~~

    @Test
    void testDeleteExistingArtistSucceeds() {
        Artist testArtist = new Artist();
        testArtist.setArtistName("Test Artist");

        when(artistRepository.findById(1L))
                .thenReturn(Optional.of(testArtist));

        assertDoesNotThrow(() -> artistService.deleteArtist(1L));

        // Verify service calls repository methods correctly
        verify(artistRepository).delete(testArtist);
    }
}

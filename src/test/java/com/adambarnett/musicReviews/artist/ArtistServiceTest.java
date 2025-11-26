package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.artist.artistdata.RequestArtistDTO;
import com.adambarnett.musicReviews.artist.artistdata.ResponseArtistDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    //~~~~~~~getArtistByName() tests~~~~~~~~~~~
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

    //~~~~~~~addArtist() tests~~~~~~~~~~~
    @Test
    void testAddNewArtistSavedInRepository() {

        Artist testArtist2 = new Artist();
        testArtist2.setArtistName("Test Artist");

        RequestArtistDTO requestDTO = new RequestArtistDTO("Test Artist");
        artistService.addArtist(requestDTO);

        when(artistRepository.findByArtistName("Test Artist"))
                .thenReturn(Optional.of(testArtist2));

        ResponseArtistDTO responseArtistDTO = artistService.getArtistByName("Test Artist");
        assertEquals("Test Artist",  responseArtistDTO.artistName());
    }

    @Test
    void testAddExistingArtistThrowsResponseStatusException() {
        Artist testArtist = new Artist();
        testArtist.setArtistName("Test Artist");

        RequestArtistDTO requestDTO = new RequestArtistDTO("Test Artist");
        artistService.addArtist(requestDTO);

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
}

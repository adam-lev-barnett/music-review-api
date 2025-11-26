package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.artist.artistdata.ResponseArtistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    private Artist testArtist;

    /** Needed to match return values of DTOs*/
    private ResponseArtistDTO testArtistDTO;

    @BeforeEach
    void initializeTestArtistToRepository() {
        this.testArtist = new Artist();
        testArtist.setArtistName("Test Artist");

        when(artistRepository.findByArtistName("Test Artist"))
                .thenReturn(Optional.of(testArtist));
    }

    @Test
    public void testGetExistingArtistByName() {

        ResponseArtistDTO responseArtistDTO = artistService.getArtistByName("Test Artist");
        assertEquals("Test Artist", responseArtistDTO.artistName());

    }

}

package com.adambarnett.musicReviews.artist;

import com.adambarnett.musicReviews.artist.artistdata.RequestArtistDTO;
import com.adambarnett.musicReviews.artist.artistdata.ResponseArtistDTO;
import com.adambarnett.musicReviews.contributor.contributordata.ResponseContributorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/name/{artistName}")
    public ResponseArtistDTO getArtistByName(@PathVariable String artistName) {
        return artistService.getArtistByName(artistName);
    }

    @GetMapping
    public List<ResponseArtistDTO> getArtists() {
        return artistService.getArtists();
    }

    @PostMapping
    public ResponseArtistDTO saveArtist(@RequestBody RequestArtistDTO requestArtistDTO) {
        return artistService.addArtist(requestArtistDTO);
    }

    @PutMapping("{id}")
    public ResponseArtistDTO updateArtist(@PathVariable Long id, @RequestBody RequestArtistDTO artist) {
        return artistService.updateArtist(id, artist);
    }

    @DeleteMapping("{id}")
    public ResponseArtistDTO deleteArtist(@PathVariable Long id) {
        return artistService.deleteArtist(id);
    }
}

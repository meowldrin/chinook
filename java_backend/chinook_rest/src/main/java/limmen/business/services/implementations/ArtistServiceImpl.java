package limmen.business.services.implementations;

import limmen.business.services.ArtistService;
import limmen.business.services.exceptions.SortException;
import limmen.business.services.filters.ArtistFilter;
import limmen.integration.entities.Artist;
import limmen.integration.repositories.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the ArtistService interface, uses a repository for database interaction.
 *
 * @author Kim Hammar on 2016-03-22.
 */
@Service
public class ArtistServiceImpl implements ArtistService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ArtistRepository artistRepository;

    @Inject
    public ArtistServiceImpl(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> getAllArtists(ArtistFilter artistFilter) throws SortException {
        List<Artist> artists = getAllArtists();
        artists = artistFilter.filter(artists);
        try {
            return artistFilter.sort(artists);
        } catch (Exception e) {
            throw new SortException("Invalid query string for sorting: " + artistFilter.getSort());
        }
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    @Override
    public Artist getArtist(int artistId) {
        return artistRepository.getArtist(artistId);
    }

    @Override
    public Artist createNewArtist(Artist artist) {
        artist.setArtistId(artistRepository.getMaxId() + 1);
        return artistRepository.createNewArtist(artist);
    }

    @Override
    public Artist updateArtist(Artist artist) {
        return artistRepository.updateArtist(artist);
    }

    @Override
    public List<Artist> updateArtists(List<Artist> artists) {
        artistRepository.deleteArtists();
        artists.forEach((artist) -> {
            createNewArtist(artist);
        });
        return getAllArtists();
    }

    @Override
    public Artist deleteArtist(int artistId) {
        Artist artist = getArtist(artistId);
        artistRepository.deleteArtist(artistId);
        return artist;
    }

}

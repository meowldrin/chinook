package limmen.business.resourcerepresentations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import limmen.integration.entities.Artist;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Kim Hammar on 2016-03-22.
 */
public class ArtistRepresentation extends ResourceSupport {
    private final Artist artist;

    @JsonCreator
    public ArtistRepresentation(@JsonProperty("artist") Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }
}

package limmen.controllers;

import limmen.business.resourcerepresentations.AlbumRepresentation;
import limmen.business.resourcerepresentations.AlbumsArrayRepresentation;
import limmen.business.services.AlbumService;
import limmen.integration.entities.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Kim Hammar on 2016-03-22.
 */
@RestController
@RequestMapping("/resources/albums")
public class AlbumController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AlbumService albumService;

    @Inject
    public AlbumController(final AlbumService albumService) {
        this.albumService = albumService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AlbumsArrayRepresentation> getAllAlbums() {
        log.debug("HTTP GET-request /resources/albums");
        List<AlbumRepresentation> albumRepresentations = new ArrayList();
        List<Album> albums = albumService.getAllAlbums();
        albums.forEach((album) -> {
            AlbumRepresentation albumRepresentation = new AlbumRepresentation(album);
            albumRepresentation.add(linkTo(methodOn(AlbumController.class).getAlbum(album.getAlbumId())).withSelfRel());
            albumRepresentation.add(linkTo(methodOn(ArtistController.class).getArtist(albumRepresentation.getAlbum()
                    .getArtistId())).withRel("artist"));
            albumRepresentations.add(albumRepresentation);
        });
        AlbumsArrayRepresentation arrayRepresentation = new AlbumsArrayRepresentation(albumRepresentations);
        return new ResponseEntity<AlbumsArrayRepresentation>(arrayRepresentation, HttpStatus.OK);
    }

    @RequestMapping(value = "/{albumId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AlbumRepresentation> getAlbum(@PathVariable int albumId) {
        log.debug("HTTP GET-request /resources/albums/{}", albumId);
        AlbumRepresentation albumRepresentation = new AlbumRepresentation(albumService.getAlbum(albumId));
        albumRepresentation.add(linkTo(methodOn(AlbumController.class).getAlbum(albumId)).withSelfRel());
        albumRepresentation.add(linkTo(methodOn(ArtistController.class).getArtist(albumRepresentation.getAlbum()
                .getArtistId())).withRel("artist"));
        return new ResponseEntity<AlbumRepresentation>(albumRepresentation, HttpStatus.OK);
    }
}

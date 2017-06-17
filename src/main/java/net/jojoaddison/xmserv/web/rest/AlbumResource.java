package net.jojoaddison.xmserv.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import net.jojoaddison.xmserv.domain.Album;
import net.jojoaddison.xmserv.domain.Media;
import net.jojoaddison.xmserv.service.AlbumService;
import net.jojoaddison.xmserv.service.MediaService;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Album.
 */
@RestController
@RequestMapping("/api")
public class AlbumResource {

    private final Logger log = LoggerFactory.getLogger(AlbumResource.class);

    private static final String ENTITY_NAME = "album";
        
    private final AlbumService albumService;
    private final MediaService mediaService;


    public AlbumResource(AlbumService albumService, MediaService mediaService) {
        this.albumService = albumService;
        this.mediaService = mediaService;
    }

    /**
     * POST  /albums : Create a new album.
     *
     * @param album the album to create
     * @return the ResponseEntity with status 201 (Created) and with body the new album, or with status 400 (Bad Request) if the album has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/albums")
    @Timed

    public Album createAlbum(@Valid @RequestBody Album album) throws URISyntaxException {
        log.debug("REST request to save Album : {}", album);
        if (album.getId() != null) {
            return updateAlbum(album);
        }
        Album result = albumService.createAlbumImage(album);
        result = albumService.createThumbnail(result);
        result = mediaService.handleMedia(result);
        result = albumService.justSave(result); 
        return result;
    }
   

	/**
     * PUT  /albums : Updates an existing album.
     *
     * @param album the album to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated album,
     * or with status 400 (Bad Request) if the album is not valid,
     * or with status 500 (Internal Server Error) if the album couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/albums")
    @Timed
    public Album updateAlbum(@Valid @RequestBody Album album) throws URISyntaxException {
        log.debug("REST request to update Album : {}", album);
        if (album.getId() == null) {
            return createAlbum(album);
        }
        Album result = albumService.createAlbumImage(album);
        result = albumService.createThumbnail(result);
        result = mediaService.handleMedia(result);
        result = albumService.justSave(result); 
        return (result);
    }

    /**
     * GET  /albums : get all the albums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of albums in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/albums")
    @Timed
    public ResponseEntity<List<Album>> getAllAlbums(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Albums");
        Page<Album> page = albumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/albums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /albums/:id : get the "id" album.
     *
     * @param id the id of the album to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the album, or with status 404 (Not Found)
     */
    @GetMapping("/albums/{id}")
    @Timed
    public ResponseEntity<Album> getAlbum(@PathVariable String id) {
        log.debug("REST request to get Album : {}", id);
        Album album = albumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(album));
    }

    /**
     * DELETE  /albums/:id : delete the "id" album.
     *
     * @param id the id of the album to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        log.debug("REST request to delete Album : {}", id);
        Album album = albumService.findOne(id);
        if(album.getMedia() != null){
        	for(Media media: album.getMedia()){
        		mediaService.delete(media);
        	}
        }
        albumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * DELETE  /albums/media{} : delete the "media" media.
     *
     * @param media the media of the album to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/albums/media")
    @Timed
    public String deleteAlbum(@RequestBody Media media) {
        log.debug("REST request to delete Album Media : {}", media);
			boolean res = mediaService.delete(media);
        return String.valueOf(res);
    }
	

}

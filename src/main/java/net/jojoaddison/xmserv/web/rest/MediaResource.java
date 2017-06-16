package net.jojoaddison.xmserv.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import net.jojoaddison.xmserv.domain.Media;
import net.jojoaddison.xmserv.security.AuthoritiesConstants;
import net.jojoaddison.xmserv.service.MediaService;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

    private final Logger log = LoggerFactory.getLogger(MediaResource.class);

    private static final String ENTITY_NAME = "media";
        
    private final MediaService mediaService;

    public MediaResource(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * POST  /media : Create a new media.
     *
     * @param media the media to create
     * @return the ResponseEntity with status 201 (Created) and with body the new media, or with status 400 (Bad Request) if the media has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media")
    @Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public ResponseEntity<Media> createMedia(@RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to save Media : {}", media);
        if (media.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new media cannot already have an ID")).body(null);
        }
        media.setCreatedDate(ZonedDateTime.now());
        media.setModifiedDate(ZonedDateTime.now());
        Media result = mediaService.saveOnly(media);
        return ResponseEntity.created(new URI("/api/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media : Updates an existing media.
     *
     * @param media the media to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated media,
     * or with status 400 (Bad Request) if the media is not valid,
     * or with status 500 (Internal Server Error) if the media couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media")
    @Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public ResponseEntity<Media> updateMedia(@RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to update Media : {}", media);
        if (media.getId() == null) {
            return createMedia(media);
        }
        media.setModifiedDate(ZonedDateTime.now());
        Media result = mediaService.save(media);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, media.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media : get all the media.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of media in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/media")
    @Timed
    public ResponseEntity<List<Media>> getAllMedia(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Media");
        Page<Media> page = mediaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/media");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /media/:id : get the "id" media.
     *
     * @param id the id of the media to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the media, or with status 404 (Not Found)
     */
    @GetMapping("/media/{id}")
    @Timed
    public ResponseEntity<Media> getMedia(@PathVariable String id) {
        log.debug("REST request to get Media : {}", id);
        Media media = mediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(media));
    }

    /**
     * DELETE  /media/:id : delete the "id" media.
     *
     * @param id the id of the media to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media/{id}")
    @Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public ResponseEntity<Void> deleteMedia(@PathVariable String id) {
        log.debug("REST request to delete Media : {}", id);        
        mediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

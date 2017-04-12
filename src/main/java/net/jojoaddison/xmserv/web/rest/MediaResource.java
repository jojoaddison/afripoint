package net.jojoaddison.xmserv.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jojoaddison.xmserv.domain.Media;

import net.jojoaddison.xmserv.repository.MediaRepository;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

    private final Logger log = LoggerFactory.getLogger(MediaResource.class);

    private static final String ENTITY_NAME = "media";
        
    private final MediaRepository mediaRepository;

    public MediaResource(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
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
    public ResponseEntity<Media> createMedia(@RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to save Media : {}", media);
        if (media.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new media cannot already have an ID")).body(null);
        }
        media.setCreatedDate(ZonedDateTime.now());
        media.setModifiedDate(ZonedDateTime.now());
        Media result = mediaRepository.save(media);
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
    public ResponseEntity<Media> updateMedia(@RequestBody Media media) throws URISyntaxException {
        log.debug("REST request to update Media : {}", media);
        if (media.getId() == null) {
            return createMedia(media);
        }
        media.setModifiedDate(ZonedDateTime.now());
        Media result = mediaRepository.save(media);
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
        Page<Media> page = mediaRepository.findAll(pageable);
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
        Media media = mediaRepository.findOne(id);
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
    public ResponseEntity<Void> deleteMedia(@PathVariable String id) {
        log.debug("REST request to delete Media : {}", id);
        mediaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

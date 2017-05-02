package net.jojoaddison.xmserv.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
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
import net.jojoaddison.xmserv.domain.Gallery;
import net.jojoaddison.xmserv.service.GalleryService;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Gallery.
 */
@RestController
@RequestMapping("/api")
public class GalleryResource {

    private final Logger log = LoggerFactory.getLogger(GalleryResource.class);

    private static final String ENTITY_NAME = "gallery";
        
    private final GalleryService galleryService;

    private final Environment env;
    
	private String GALLERY_PHOTOS = "/app/entities/gallery/photos";

    public GalleryResource(GalleryService galleryService, Environment env) {
        this.galleryService = galleryService;
        this.env = env;
    }

    /**
     * POST  /galleries : Create a new gallery.
     *
     * @param gallery the gallery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gallery, or with status 400 (Bad Request) if the gallery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/galleries")
    @Timed
    public ResponseEntity<Gallery> createGallery(@Valid @RequestBody Gallery gallery) throws URISyntaxException {
        log.debug("REST request to save Gallery : {}", gallery);
        if (gallery.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gallery cannot already have an ID")).body(null);
        }
        Gallery result = galleryService.save(gallery);
        return ResponseEntity.created(new URI("/api/galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /galleries : Updates an existing gallery.
     *
     * @param gallery the gallery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gallery,
     * or with status 400 (Bad Request) if the gallery is not valid,
     * or with status 500 (Internal Server Error) if the gallery couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/galleries")
    @Timed
    public ResponseEntity<Gallery> updateGallery(@Valid @RequestBody Gallery gallery) throws URISyntaxException {
        log.debug("REST request to update Gallery : {}", gallery);
        if (gallery.getId() == null) {
            return createGallery(gallery);
        }
        Gallery result = galleryService.save(gallery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gallery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /galleries : get all the galleries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of galleries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/galleries")
    @Timed
    public ResponseEntity<List<Gallery>> getAllGalleries(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Galleries");
        Page<Gallery> page = galleryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/galleries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /galleries/:id : get the "id" gallery.
     *
     * @param id the id of the gallery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gallery, or with status 404 (Not Found)
     */
    @GetMapping("/galleries/{id}")
    @Timed
    public ResponseEntity<Gallery> getGallery(@PathVariable String id) {
        log.debug("REST request to get Gallery : {}", id);
        Gallery gallery = galleryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gallery));
    }

    /**
     * DELETE  /galleries/:id : delete the "id" gallery.
     *
     * @param id the id of the gallery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/galleries/{id}")
    @Timed
    public ResponseEntity<Void> deleteGallery(@PathVariable String id) {
        log.debug("REST request to delete Gallery : {}", id);
        galleryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    

}

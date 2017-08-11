package net.jojoaddison.xmserv.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jojoaddison.xmserv.domain.InfoScreen;

import net.jojoaddison.xmserv.repository.InfoScreenRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InfoScreen.
 */
@RestController
@RequestMapping("/api")
public class InfoScreenResource {

    private final Logger log = LoggerFactory.getLogger(InfoScreenResource.class);

    private static final String ENTITY_NAME = "infoScreen";
        
    private final InfoScreenRepository infoScreenRepository;

    public InfoScreenResource(InfoScreenRepository infoScreenRepository) {
        this.infoScreenRepository = infoScreenRepository;
    }

    /**
     * POST  /info-screens : Create a new infoScreen.
     *
     * @param infoScreen the infoScreen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new infoScreen, or with status 400 (Bad Request) if the infoScreen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/info-screens")
    @Timed
    public ResponseEntity<InfoScreen> createInfoScreen(@Valid @RequestBody InfoScreen infoScreen) throws URISyntaxException {
        log.debug("REST request to save InfoScreen : {}", infoScreen);
        if (infoScreen.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new infoScreen cannot already have an ID")).body(null);
        }
        InfoScreen result = infoScreenRepository.save(infoScreen);
        return ResponseEntity.created(new URI("/api/info-screens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /info-screens : Updates an existing infoScreen.
     *
     * @param infoScreen the infoScreen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated infoScreen,
     * or with status 400 (Bad Request) if the infoScreen is not valid,
     * or with status 500 (Internal Server Error) if the infoScreen couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/info-screens")
    @Timed
    public ResponseEntity<InfoScreen> updateInfoScreen(@Valid @RequestBody InfoScreen infoScreen) throws URISyntaxException {
        log.debug("REST request to update InfoScreen : {}", infoScreen);
        if (infoScreen.getId() == null) {
            return createInfoScreen(infoScreen);
        }
        InfoScreen result = infoScreenRepository.save(infoScreen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, infoScreen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /info-screens : get all the infoScreens.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of infoScreens in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/info-screens")
    @Timed
    public ResponseEntity<List<InfoScreen>> getAllInfoScreens(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of InfoScreens");
        Page<InfoScreen> page = infoScreenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/info-screens");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /info-screens/:id : get the "id" infoScreen.
     *
     * @param id the id of the infoScreen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the infoScreen, or with status 404 (Not Found)
     */
    @GetMapping("/info-screens/{id}")
    @Timed
    public ResponseEntity<InfoScreen> getInfoScreen(@PathVariable String id) {
        log.debug("REST request to get InfoScreen : {}", id);
        InfoScreen infoScreen = infoScreenRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(infoScreen));
    }

    /**
     * DELETE  /info-screens/:id : delete the "id" infoScreen.
     *
     * @param id the id of the infoScreen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/info-screens/{id}")
    @Timed
    public ResponseEntity<Void> deleteInfoScreen(@PathVariable String id) {
        log.debug("REST request to delete InfoScreen : {}", id);
        infoScreenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

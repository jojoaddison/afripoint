package net.jojoaddison.xmserv.web.rest;

import org.springframework.security.access.annotation.Secured;
import net.jojoaddison.xmserv.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import net.jojoaddison.xmserv.domain.AfripointService;

import net.jojoaddison.xmserv.repository.AfripointServiceRepository;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AfripointService.
 */
@RestController
@RequestMapping("/api")
public class AfripointServiceResource {

    private final Logger log = LoggerFactory.getLogger(AfripointServiceResource.class);

    private static final String ENTITY_NAME = "afripointService";

    private final AfripointServiceRepository afripointServiceRepository;

    public AfripointServiceResource(AfripointServiceRepository afripointServiceRepository) {
        this.afripointServiceRepository = afripointServiceRepository;
    }

    /**
     * POST  /afripoint-services : Create a new afripointService.
     *
     * @param afripointService the afripointService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new afripointService, or with status 400 (Bad Request) if the afripointService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/afripoint-services")
	  @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    @Timed
    public ResponseEntity<AfripointService> createAfripointService(@RequestBody AfripointService afripointService) throws URISyntaxException {
        log.debug("REST request to save AfripointService : {}", afripointService);
        if (afripointService.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new afripointService cannot already have an ID")).body(null);
        }
        AfripointService result = afripointServiceRepository.save(afripointService);
        return ResponseEntity.created(new URI("/api/afripoint-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /afripoint-services : Updates an existing afripointService.
     *
     * @param afripointService the afripointService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated afripointService,
     * or with status 400 (Bad Request) if the afripointService is not valid,
     * or with status 500 (Internal Server Error) if the afripointService couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/afripoint-services")
	  @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    @Timed
    public ResponseEntity<AfripointService> updateAfripointService(@RequestBody AfripointService afripointService) throws URISyntaxException {
        log.debug("REST request to update AfripointService : {}", afripointService);
        if (afripointService.getId() == null) {
            return createAfripointService(afripointService);
        }
        AfripointService result = afripointServiceRepository.save(afripointService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, afripointService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /afripoint-services : get all the afripointServices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of afripointServices in body
     */
    @GetMapping("/afripoint-services")
    @Timed
    public List<AfripointService> getAllAfripointServices() {
        log.debug("REST request to get all AfripointServices");
        List<AfripointService> afripointServices = afripointServiceRepository.findAll();
        return afripointServices;
    }

    /**
     * GET  /afripoint-services/:id : get the "id" afripointService.
     *
     * @param id the id of the afripointService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the afripointService, or with status 404 (Not Found)
     */
    @GetMapping("/afripoint-services/{id}")
    @Timed
    public ResponseEntity<AfripointService> getAfripointService(@PathVariable String id) {
        log.debug("REST request to get AfripointService : {}", id);
        AfripointService afripointService = afripointServiceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(afripointService));
    }

    /**
     * DELETE  /afripoint-services/:id : delete the "id" afripointService.
     *
     * @param id the id of the afripointService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/afripoint-services/{id}")
	  @Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    @Timed
    public ResponseEntity<Void> deleteAfripointService(@PathVariable String id) {
        log.debug("REST request to delete AfripointService : {}", id);
        afripointServiceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

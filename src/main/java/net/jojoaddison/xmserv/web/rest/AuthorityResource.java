package net.jojoaddison.xmserv.web.rest;

import java.net.URI;
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
import net.jojoaddison.xmserv.domain.Authority;
import net.jojoaddison.xmserv.repository.AuthorityRepository;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Authority.
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    private static final String ENTITY_NAME = "authority";
        
    private final AuthorityRepository authorityRepository;

    public AuthorityResource(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    /**
     * POST  /authorities : Create a new authority.
     *
     * @param authority the authority to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authority, or with status 400 (Bad Request) if the authority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorities")
    @Timed
    public ResponseEntity<Authority> createAuthority(@Valid @RequestBody Authority authority) throws URISyntaxException {
        log.debug("REST request to save Authority : {}", authority);        
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.created(new URI("/api/authorities/" + result.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getName().toString()))
            .body(result);
    }

    /**
     * PUT  /authorities : Updates an existing authority.
     *
     * @param authority the authority to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authority,
     * or with status 400 (Bad Request) if the authority is not valid,
     * or with status 500 (Internal Server Error) if the authority couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorities")
    @Timed
    public ResponseEntity<Authority> updateAuthority(@Valid @RequestBody Authority authority) throws URISyntaxException {
        log.debug("REST request to update Authority : {}", authority);        
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authority.getName().toString()))
            .body(result);
    }

    /**
     * GET  /authorities : get all the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/authorities")
    @Timed
    public ResponseEntity<List<Authority>> getAllAuthorities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Authorities");
        Page<Authority> page = authorityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authorities/:id : get the "id" authority.
     *
     * @param id the id of the authority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authority, or with status 404 (Not Found)
     */
    @GetMapping("/authorities/{name}")
    @Timed
    public ResponseEntity<Authority> getAuthority(@PathVariable String name) {
        log.debug("REST request to get Authority : {}", name);
        Authority authority = authorityRepository.findByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authority));
    }

    /**
     * DELETE  /authorities/:id : delete the "id" authority.
     *
     * @param id the id of the authority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authorities/{name}")
    @Timed
    public ResponseEntity<Void> deleteAuthority(@PathVariable String name) {
        log.debug("REST request to delete Authority : {}", name);
        authorityRepository.delete(name);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, name.toString())).build();
    }

}

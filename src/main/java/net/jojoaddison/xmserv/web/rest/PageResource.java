package net.jojoaddison.xmserv.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import net.jojoaddison.xmserv.domain.Page;
import net.jojoaddison.xmserv.repository.PageRepository;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Page.
 */
@RestController
@RequestMapping("/api")
public class PageResource {

    private final Logger log = LoggerFactory.getLogger(PageResource.class);

    private static final String ENTITY_NAME = "page";
        
    private final PageRepository pageRepository;

    public PageResource(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    /**
     * POST  /pages : Create a new page.
     *
     * @param page the page to create
     * @return the ResponseEntity with status 201 (Created) and with body the new page, or with status 400 (Bad Request) if the page has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pages")
    @Timed
    public ResponseEntity<Page> createPage(@Valid @RequestBody Page page) throws URISyntaxException {
        log.debug("REST request to save Page : {}", page);
        if (page.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new page cannot already have an ID")).body(null);
        }
        Page result = pageRepository.save(page);
        return ResponseEntity.created(new URI("/api/pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pages : Updates an existing page.
     *
     * @param page the page to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated page,
     * or with status 400 (Bad Request) if the page is not valid,
     * or with status 500 (Internal Server Error) if the page couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pages")
    @Timed
    public ResponseEntity<Page> updatePage(@Valid @RequestBody Page page) throws URISyntaxException {
        log.debug("REST request to update Page : {}", page);
        if (page.getId() == null) {
            return createPage(page);
        }
        Page result = pageRepository.save(page);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, page.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pages : get all the pages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pages in body
     */
    @GetMapping("/pages")
    @Timed
    public List<Page> getAllPages() {
        log.debug("REST request to get all Pages");
        List<Page> pages = pageRepository.findAll();
        return pages;
    }

    /**
     * GET  /pages/:id : get the "id" page.
     *
     * @param id the id of the page to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the page, or with status 404 (Not Found)
     */
    @GetMapping("/pages/{id}")
    @Timed
    public ResponseEntity<Page> getPage(@PathVariable String id) {
        log.debug("REST request to get Page : {}", id);
        Page page = pageRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(page));
    }

    /**
     * DELETE  /pages/:id : delete the "id" page.
     *
     * @param id the id of the page to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pages/{id}")
    @Timed
    public ResponseEntity<Void> deletePage(@PathVariable String id) {
        log.debug("REST request to delete Page : {}", id);
        pageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

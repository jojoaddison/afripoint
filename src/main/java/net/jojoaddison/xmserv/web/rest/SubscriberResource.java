package net.jojoaddison.xmserv.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jojoaddison.xmserv.domain.Subscriber;

import net.jojoaddison.xmserv.repository.SubscriberRepository;
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
 * REST controller for managing Subscriber.
 */
@RestController
@RequestMapping("/api")
public class SubscriberResource {

    private final Logger log = LoggerFactory.getLogger(SubscriberResource.class);

    private static final String ENTITY_NAME = "subscriber";
        
    private final SubscriberRepository subscriberRepository;

    public SubscriberResource(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * POST  /subscribers : Create a new subscriber.
     *
     * @param subscriber the subscriber to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriber, or with status 400 (Bad Request) if the subscriber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subscribers")
    @Timed
    public ResponseEntity<Subscriber> createSubscriber(@Valid @RequestBody Subscriber subscriber) throws URISyntaxException {
        log.debug("REST request to save Subscriber : {}", subscriber);
        if (subscriber.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subscriber cannot already have an ID")).body(null);
        }
        Subscriber result = subscriberRepository.save(subscriber);
        return ResponseEntity.created(new URI("/api/subscribers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscribers : Updates an existing subscriber.
     *
     * @param subscriber the subscriber to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriber,
     * or with status 400 (Bad Request) if the subscriber is not valid,
     * or with status 500 (Internal Server Error) if the subscriber couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subscribers")
    @Timed
    public ResponseEntity<Subscriber> updateSubscriber(@Valid @RequestBody Subscriber subscriber) throws URISyntaxException {
        log.debug("REST request to update Subscriber : {}", subscriber);
        if (subscriber.getId() == null) {
            return createSubscriber(subscriber);
        }
        Subscriber result = subscriberRepository.save(subscriber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subscriber.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscribers : get all the subscribers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subscribers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/subscribers")
    @Timed
    public ResponseEntity<List<Subscriber>> getAllSubscribers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Subscribers");
        Page<Subscriber> page = subscriberRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subscribers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subscribers/:id : get the "id" subscriber.
     *
     * @param id the id of the subscriber to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriber, or with status 404 (Not Found)
     */
    @GetMapping("/subscribers/{id}")
    @Timed
    public ResponseEntity<Subscriber> getSubscriber(@PathVariable String id) {
        log.debug("REST request to get Subscriber : {}", id);
        Subscriber subscriber = subscriberRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscriber));
    }

    /**
     * DELETE  /subscribers/:id : delete the "id" subscriber.
     *
     * @param id the id of the subscriber to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subscribers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubscriber(@PathVariable String id) {
        log.debug("REST request to delete Subscriber : {}", id);
        subscriberRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

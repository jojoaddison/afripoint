package net.jojoaddison.xmserv.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import net.jojoaddison.xmserv.domain.LocationItem;
import net.jojoaddison.xmserv.service.LocationItemService;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;

/**
 * REST controller for managing LocationOrder.
 */
@RestController
@RequestMapping("/api")
public class LocationItemResource {

    private final Logger log = LoggerFactory.getLogger(LocationItemResource.class);

    private static final String ENTITY_NAME = "locationItem";
        
    private final LocationItemService locationItemService;

    public LocationItemResource(LocationItemService locationItemService) {
        this.locationItemService = locationItemService;
    }

    /**
     * POST  /location-items : Create a new locationItem.
     *
     * @param locationItem the locationItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationItem, or with status 400 (Bad Request) if the locationItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/location-items")
    @Timed
    public ResponseEntity<LocationItem> createLocationOrder(@RequestBody LocationItem locationItem) throws URISyntaxException {
        log.debug("REST request to save LocationOrder : {}", locationItem);
        if (locationItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new locationItem cannot already have an ID")).body(null);
        }
        LocationItem result = locationItemService.save(locationItem);
        return ResponseEntity.created(new URI("/api/location-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /location-items : Updates an existing locationItem.
     *
     * @param locationItem the locationItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationItem,
     * or with status 400 (Bad Request) if the locationItem is not valid,
     * or with status 500 (Internal Server Error) if the locationItem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/location-items")
    @Timed
    public ResponseEntity<LocationItem> updateLocationOrder(@RequestBody LocationItem locationItem) throws URISyntaxException {
        log.debug("REST request to update LocationOrder : {}", locationItem);
        if (locationItem.getId() == null) {
            return createLocationOrder(locationItem);
        }
        LocationItem result = locationItemService.save(locationItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /location-items : get all the locationItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of locationItems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/location-items")
    @Timed
    public ResponseEntity<List<LocationItem>> getAllLocationOrders(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LocationOrders");
        Page<LocationItem> page = locationItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/location-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /location-items/:id : get the "id" locationItem.
     *
     * @param id the id of the locationItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationItem, or with status 404 (Not Found)
     */
    @GetMapping("/location-items/{id}")
    @Timed
    public ResponseEntity<LocationItem> getLocationOrder(@PathVariable String id) {
        log.debug("REST request to get LocationOrder : {}", id);
        LocationItem locationItem = locationItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationItem));
    }

    /**
     * DELETE  /location-items/:id : delete the "id" locationItem.
     *
     * @param id the id of the locationItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/location-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocationOrder(@PathVariable String id) {
        log.debug("REST request to delete LocationOrder : {}", id);
        locationItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

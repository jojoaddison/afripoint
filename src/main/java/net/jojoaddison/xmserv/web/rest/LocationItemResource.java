package net.jojoaddison.xmserv.web.rest;

<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
<<<<<<< HEAD
import org.springframework.core.env.Environment;
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
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
<<<<<<< HEAD
import net.coobird.thumbnailator.Thumbnails;
import net.jojoaddison.xmserv.domain.LocationItem;
import net.jojoaddison.xmserv.service.LocationItemService;
import net.jojoaddison.xmserv.service.util.Tools;
=======
import net.jojoaddison.xmserv.domain.LocationItem;
import net.jojoaddison.xmserv.service.LocationItemService;
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
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
<<<<<<< HEAD
    private final Environment env;

    private final String LOCATION_PHOTOS = "data/location-item/photos";
    private final String DATA = "data/";
    private final int HEIGHT = 200;
    private final int WIDTH = 200;

    public LocationItemResource(LocationItemService locationItemService, Environment env) {
        this.locationItemService = locationItemService;
        this.env = env;
=======

    public LocationItemResource(LocationItemService locationItemService) {
        this.locationItemService = locationItemService;
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
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
<<<<<<< HEAD
        locationItem = convert(locationItem);
        locationItem = createThumbnail(locationItem);
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
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
<<<<<<< HEAD
        locationItem = convert(locationItem);
        locationItem = createThumbnail(locationItem);
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
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

<<<<<<< HEAD
    private LocationItem convert(LocationItem locationItem){    	
    	if(locationItem.getImage() != null){
			log.info("converting: {}", locationItem);
    		String fileExt = locationItem.getContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String partnerDir = Tools.removeSpaces(locationItem.getCategory().toLowerCase()).concat(sep);
    		String directory = LOCATION_PHOTOS.concat(sep).concat(partnerDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
					if(locationItem.getImageUrl() != null){
						Tools.removeFile(root.concat(locationItem.getImageUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, locationItem.getImage());
					Tools.setPermission(fileName, Tools.getPermissions775());
					Tools.setReadPermissions(root.concat(DATA));
					locationItem.setImage(null);
					locationItem.setImageUrl(url);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}
    	
    	return locationItem;
    }

    private LocationItem createThumbnail(LocationItem locationItem){
    	if(locationItem.getThumbnail() == null && locationItem.getImageUrl() != null){
    		String fileExt = locationItem.getContentType().split("/")[1].toLowerCase();
    		String photoUrl = locationItem.getImageUrl();
    		String thumbnail = photoUrl.substring(0, photoUrl.lastIndexOf('.')).concat("_thumb").concat(".").concat(fileExt);
    		locationItem.setThumbnail(thumbnail);
    		String root = env.getProperty("client.root");
    		String thumbFilename = root.concat(thumbnail);
    		String photoFile = root.concat(photoUrl);
    		try {
				Thumbnails.of(new File(photoFile)).size(WIDTH, HEIGHT).outputQuality(0.8).outputFormat(fileExt).toFile(thumbFilename);
				Tools.setReadPermissions(root.concat(DATA));
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage(), e.getCause());
			}
    	}
    	return locationItem;
    }
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
}

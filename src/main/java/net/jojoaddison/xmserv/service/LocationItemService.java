package net.jojoaddison.xmserv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.jojoaddison.xmserv.domain.LocationItem;
import net.jojoaddison.xmserv.repository.LocationItemRepository;

/**
 * Service Implementation for managing LocationItem.
 */
@Service
public class LocationItemService {

    private final Logger log = LoggerFactory.getLogger(LocationItemService.class);
    
    private final LocationItemRepository locationItemRepository;

    public LocationItemService(LocationItemRepository locationItemRepository) {
        this.locationItemRepository = locationItemRepository;
    }

    /**
     * Save a locationItem.
     *
     * @param locationItem the entity to save
     * @return the persisted entity
     */
    public LocationItem save(LocationItem locationItem) {
        log.debug("Request to save LocationItem : {}", locationItem);
        LocationItem result = locationItemRepository.save(locationItem);
        return result;
    }

    /**
     *  Get all the locationItems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<LocationItem> findAll(Pageable pageable) {
        log.debug("Request to get all LocationItems");
        Page<LocationItem> result = locationItemRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one locationItem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public LocationItem findOne(String id) {
        log.debug("Request to get LocationItem : {}", id);
        LocationItem locationItem = locationItemRepository.findOne(id);
        return locationItem;
    }

    /**
     *  Delete the  locationItem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete LocationItem : {}", id);
        locationItemRepository.delete(id);
    }
}

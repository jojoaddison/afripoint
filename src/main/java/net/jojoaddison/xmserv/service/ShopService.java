package net.jojoaddison.xmserv.service;

import net.jojoaddison.xmserv.domain.Shop;
import net.jojoaddison.xmserv.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Shop.
 */
@Service
public class ShopService {

    private final Logger log = LoggerFactory.getLogger(ShopService.class);
    
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /**
     * Save a shop.
     *
     * @param shop the entity to save
     * @return the persisted entity
     */
    public Shop save(Shop shop) {
        log.debug("Request to save Shop : {}", shop);
        Shop result = shopRepository.save(shop);
        return result;
    }

    /**
     *  Get all the shops.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Shop> findAll(Pageable pageable) {
        log.debug("Request to get all Shops");
        Page<Shop> result = shopRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one shop by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Shop findOne(String id) {
        log.debug("Request to get Shop : {}", id);
        Shop shop = shopRepository.findOne(id);
        return shop;
    }

    /**
     *  Delete the  shop by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Shop : {}", id);
        shopRepository.delete(id);
    }
}

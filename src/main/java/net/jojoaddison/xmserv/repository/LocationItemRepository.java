package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.LocationItem;

/**
 * Spring Data MongoDB repository for the LocationOrder entity.
 */
@SuppressWarnings("unused")
public interface LocationItemRepository extends MongoRepository<LocationItem,String> {

}

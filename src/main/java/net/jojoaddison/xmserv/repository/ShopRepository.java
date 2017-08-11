package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Shop;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Shop entity.
 */
@SuppressWarnings("unused")
public interface ShopRepository extends MongoRepository<Shop,String> {

}

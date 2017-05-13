package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Partner;

/**
 * Spring Data MongoDB repository for the Partner entity.
 */
@SuppressWarnings("unused")
public interface PartnerRepository extends MongoRepository<Partner,String> {

}

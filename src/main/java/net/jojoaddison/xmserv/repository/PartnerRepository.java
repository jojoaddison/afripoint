package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Partner;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Partner entity.
 */
@SuppressWarnings("unused")
public interface PartnerRepository extends MongoRepository<Partner,String> {

}

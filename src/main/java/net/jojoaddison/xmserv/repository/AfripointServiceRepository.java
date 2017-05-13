package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Afripoint;

/**
 * Spring Data MongoDB repository for the AfripointService entity.
 */
public interface AfripointServiceRepository extends MongoRepository<Afripoint,String> {

}

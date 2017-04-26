package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Afripoint;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AfripointService entity.
 */
public interface AfripointServiceRepository extends MongoRepository<Afripoint,String> {

}

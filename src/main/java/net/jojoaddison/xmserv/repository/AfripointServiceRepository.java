package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.AfripointService;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the AfripointService entity.
 */
@SuppressWarnings("unused")
public interface AfripointServiceRepository extends MongoRepository<AfripointService,String> {

}

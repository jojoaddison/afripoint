package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.InfoScreen;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the InfoScreen entity.
 */
@SuppressWarnings("unused")
public interface InfoScreenRepository extends MongoRepository<InfoScreen,String> {

}

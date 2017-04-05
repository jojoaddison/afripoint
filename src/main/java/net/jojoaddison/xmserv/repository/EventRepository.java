package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Event;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
@SuppressWarnings("unused")
public interface EventRepository extends MongoRepository<Event,String> {

}

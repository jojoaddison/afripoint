package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Subscriber;

/**
 * Spring Data MongoDB repository for the Subscriber entity.
 */
@SuppressWarnings("unused")
public interface SubscriberRepository extends MongoRepository<Subscriber,String> {

}

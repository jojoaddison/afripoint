package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Subscriber;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Subscriber entity.
 */
@SuppressWarnings("unused")
public interface SubscriberRepository extends MongoRepository<Subscriber,String> {

}

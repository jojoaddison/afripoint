package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Media;

/**
 * Spring Data MongoDB repository for the Media entity.
 */
@SuppressWarnings("unused")
public interface MediaRepository extends MongoRepository<Media,String> {

}

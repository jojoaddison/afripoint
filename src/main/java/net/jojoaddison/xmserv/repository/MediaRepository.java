package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Media;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Media entity.
 */
@SuppressWarnings("unused")
public interface MediaRepository extends MongoRepository<Media,String> {

}

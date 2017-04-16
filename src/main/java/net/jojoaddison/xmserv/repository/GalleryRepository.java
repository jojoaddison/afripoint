package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Gallery;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Gallery entity.
 */
@SuppressWarnings("unused")
public interface GalleryRepository extends MongoRepository<Gallery,String> {

}

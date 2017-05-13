package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Gallery;

/**
 * Spring Data MongoDB repository for the Gallery entity.
 */
@SuppressWarnings("unused")
public interface GalleryRepository extends MongoRepository<Gallery,String> {

}

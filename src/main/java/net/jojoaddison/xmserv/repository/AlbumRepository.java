package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Album;

/**
 * Spring Data MongoDB repository for the Album entity.
 */
@SuppressWarnings("unused")
public interface AlbumRepository extends MongoRepository<Album,String> {

}

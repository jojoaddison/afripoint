package net.jojoaddison.xmserv.repository;

import net.jojoaddison.xmserv.domain.Page;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Page entity.
 */
@SuppressWarnings("unused")
public interface PageRepository extends MongoRepository<Page,String> {

}

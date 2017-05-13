package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Page;

/**
 * Spring Data MongoDB repository for the Page entity.
 */
@SuppressWarnings("unused")
public interface PageRepository extends MongoRepository<Page,String> {

}

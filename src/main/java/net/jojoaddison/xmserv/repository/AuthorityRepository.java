package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Authority;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {

	Authority findByName(String name);
}

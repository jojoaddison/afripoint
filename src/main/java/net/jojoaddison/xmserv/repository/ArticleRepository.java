package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Article;

/**
 * Spring Data MongoDB repository for the Article entity.
 */
@SuppressWarnings("unused")
public interface ArticleRepository extends MongoRepository<Article,String> {

}

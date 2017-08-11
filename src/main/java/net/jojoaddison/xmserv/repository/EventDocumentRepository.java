package net.jojoaddison.xmserv.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import net.jojoaddison.xmserv.domain.EventDocument;

/**
 * Spring Data MongoDB repository for the EventDocument entity.
 */
public interface EventDocumentRepository extends MongoRepository<EventDocument,String> {

}

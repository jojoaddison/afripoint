package net.jojoaddison.xmserv.repository;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.jojoaddison.xmserv.domain.Event;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
public interface EventRepository extends MongoRepository<Event,String> {

	Page<Event> findAllByStartTimeBefore(ZonedDateTime minusDays, Pageable pageable); // Historical

	Page<Event> findAllByStartTimeAfter(ZonedDateTime minusDays, Pageable pageable); // Future upcoming

}

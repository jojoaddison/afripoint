package net.jojoaddison.xmserv.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.jojoaddison.xmserv.domain.Event;
import net.jojoaddison.xmserv.repository.EventRepository;
import net.jojoaddison.xmserv.service.util.Tools;

/**
 * Service Implementation for managing Event.
 */
@Service
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    private final Environment env;

    private final String EVENT_PHOTOS = "/app/entities/event/photos";


    public EventService(EventRepository eventRepository, Environment env) {
        this.eventRepository = eventRepository;
        this.env = env;
    }

    /**
     * Save a event.
     *
     * @param event the entity to save
     * @return the persisted entity
     */
    public Event save(Event event) {
        log.debug("Request to save Event : {}", event);
        Event result = eventRepository.save(convertEvent(event));
        return result;
    }

    private Event convertEvent(Event event){
    	if(event.getPhoto() == null || event.getPhoto().length() < 0){
			log.info("converting: {}", event);
    		String fileExt = event.getImageContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String directory = EVENT_PHOTOS.concat(sep).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory);
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt);
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					BufferedOutputStream stream =
					          new BufferedOutputStream(new FileOutputStream(new File(fileName)));
					        stream.write(event.getImage());
					        stream.close();
					event.setImage(null);
					event.setPhoto(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}
    	return event;
    }

    /**
     *  Get all the events.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Event> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        Page<Event> result = eventRepository.findAll(pageable);
        return result;
    }


    /**
     *  Get all current events.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Event> findAllCurrent(Pageable pageable) {
        log.debug("Request to get all Events");
        ZonedDateTime now = ZonedDateTime.now();
        Page<Event> result = eventRepository.findAllByStartTimeAfter(now, pageable);
        return result;
    }

    /**
     *  Get all historical events.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Event> findAllPast(Pageable pageable) {
        log.debug("Request to get all Events");
        ZonedDateTime now = ZonedDateTime.now();
        Page<Event> result = eventRepository.findAllByStartTimeBefore(now, pageable);
        return result;
    }
    /**
     *  Get one event by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Event findOne(String id) {
        log.debug("Request to get Event : {}", id);
        Event event = eventRepository.findOne(id);
        return event;
    }

    /**
     *  Delete the  event by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.delete(id);
    }

}

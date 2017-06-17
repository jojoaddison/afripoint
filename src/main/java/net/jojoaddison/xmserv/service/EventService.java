package net.jojoaddison.xmserv.service;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
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

    private final String EVENT_PHOTOS = "data/event/photos";
    private final String DATA = "data/";
    private final int HEIGHT = 120;
    private final int WIDTH = 120;


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
        event = convertEvent(event);
        event = createThumbnail(event);        
        Event result = eventRepository.save(event);
        return result;
    }

    private Event convertEvent(Event event){
    	if(event.getImage() != null){
			log.info("converting: {}", event);
			String fileExt = event.getImageContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String directory = EVENT_PHOTOS.concat(sep).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				log.info("full path: {}", fullPath);
				if(fullPath != null){
					if(event.getPhoto() != null){
						Tools.removeFile(root.concat(event.getPhoto()));
					}
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, event.getImage());
					String thumb = directory.concat(sep).concat(Tools.getDate()).concat("_thumb").concat(".").concat(fileExt).toLowerCase();
					String thumbFileName = root.concat(thumb);
					Thumbnails.of(fileName).size(WIDTH, HEIGHT)
					.outputQuality(0.7)
					.outputFormat(fileExt)
					.toFile(thumbFileName);
					Tools.setReadPermissions(root.concat(DATA));
					Tools.setPermission(fileName, Tools.getPermissions775());
					event.setThumbnail(thumb);
					event.setImage(null);
					event.setPhoto(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}
    	return event;
    }
    
    private Event createThumbnail(Event event){
    	if(event.getThumbnail() == null && event.getPhoto() != null){
    		String fileExt = event.getImageContentType().split("/")[1].toLowerCase();
    		String photoUrl = event.getPhoto();
    		String thumbnail = photoUrl.substring(0, photoUrl.lastIndexOf('.')).concat("_thumb").concat(".").concat(fileExt);
    		event.setThumbnail(thumbnail);
    		String root = env.getProperty("client.root");
    		String thumbFilename = root.concat(thumbnail);
    		String photoFile = root.concat(photoUrl);
    		try {
				Thumbnails.of(new File(photoFile)).size(WIDTH, HEIGHT).outputQuality(0.7).outputFormat(fileExt).toFile(thumbFilename);
			} catch (IOException e) {
				e.printStackTrace();
				log.debug(e.getMessage(), e.getCause());
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
        Page<Event> result = eventRepository.findAllByStartTimeAfter(now.minusDays(2), pageable);
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

    public String createCurrentEvent(MultipartFile file){
    	try{
    		String path = "content/docs/afripoint-events.pdf";
    		String sep = Tools.getSeparator();
    	    String root = env.getProperty("client.root").concat(sep).concat(path);
    	    Tools.createFile(root, file.getBytes());
    	    return root;
    	}catch(Exception e){
    		log.error(e.getMessage(), e.getCause());
    	}
    	
    	return null;
    }

}

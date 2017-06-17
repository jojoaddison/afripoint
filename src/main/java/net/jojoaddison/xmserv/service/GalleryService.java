package net.jojoaddison.xmserv.service;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;
import net.jojoaddison.xmserv.domain.Album;
import net.jojoaddison.xmserv.domain.Gallery;
import net.jojoaddison.xmserv.repository.GalleryRepository;
import net.jojoaddison.xmserv.service.util.Tools;

/**
 * Service Implementation for managing Gallery.
 */
@Service
public class GalleryService {

    private final Logger log = LoggerFactory.getLogger(GalleryService.class);

    private final GalleryRepository galleryRepository;

    private final Environment env;
	
	private String GALLERY_PHOTOS = "data/gallery/photos";
    private final String DATA = "data/";
    private final int HEIGHT = 120;
    private final int WIDTH = 120;
	


    public GalleryService(GalleryRepository galleryRepository, Environment env) {
        this.galleryRepository = galleryRepository;
        this.env = env;
    }

    /**
     * Save a gallery.
     *
     * @param gallery the entity to save
     * @return the persisted entity
     */
    public Gallery save(Gallery gallery) {
        log.debug("Request to save Gallery : {}", gallery);      
        gallery = createGalleryImage(gallery);
        gallery = createThumbnail(gallery);
        Gallery result = galleryRepository.save(gallery);
        return result;
    }
    
/**
    private void setPermissions(String dir) throws IOException, InterruptedException{
    	String command = "chmod a+rx -R " + dir;
    	log.debug("Execute: {}", command);
    	Process permProcess = Runtime.getRuntime().exec(command);
    	if(permProcess.waitFor() == 0){
    		log.debug("completed");
    	}
    }
*/

    /**
     *  Get all the events.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Gallery> findAll(Pageable pageable) {
        log.debug("Request to get all Galleries");
        Page<Gallery> result = galleryRepository.findAll(pageable);
        return result;
    }


    /**
     *  Get one gallery by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Gallery findOne(String id) {
        log.debug("Request to get Gallery : {}", id);
        Gallery gallery = galleryRepository.findOne(id);
        return gallery;
    }

    /**
     *  Delete the  gallery by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Gallery : {}", id);
        galleryRepository.delete(id);
    }


    private Gallery createGalleryImage(Gallery gallery){
    	if(gallery.getPicture() != null){
			log.info("converting gallery: {}", gallery);
    		String fileExt = gallery.getPictureContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String directory = GALLERY_PHOTOS.concat(sep).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
//					Tools.setPermissions(root.concat(GALLERY_PHOTOS), Tools.getReadPermissions());
					if(gallery.getPictureUrl() != null){
						Tools.removeFile(root.concat(gallery.getPictureUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, gallery.getPicture());
					String thumb = directory.concat(sep).concat(Tools.getDate()).concat("_thumb").concat(".").concat(fileExt).toLowerCase();
					String thumbFileName = root.concat(thumb);
					Thumbnails.of(fileName).size(WIDTH, HEIGHT)
					.outputQuality(0.7)
					.outputFormat(fileExt)
					.toFile(thumbFileName);
					gallery.setThumbnail(thumb);
					gallery.setPicture(null);
					gallery.setPictureUrl(url);
					Tools.setReadPermissions(root.concat(DATA));
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}
    	return gallery;
    }


    public Gallery createThumbnail(Gallery gallery){
    	if(gallery.getThumbnail() == null && gallery.getPictureUrl() != null){
    		String fileExt = gallery.getPictureContentType().split("/")[1].toLowerCase();
    		String photoUrl = gallery.getPictureUrl();
    		String thumbnail = photoUrl.substring(0, photoUrl.lastIndexOf('.')).concat("_thumb").concat(".").concat(fileExt);
    		gallery.setThumbnail(thumbnail);
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
    	return gallery;
    }
    

}

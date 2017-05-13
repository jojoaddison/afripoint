package net.jojoaddison.xmserv.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.jojoaddison.xmserv.domain.Album;
import net.jojoaddison.xmserv.domain.Gallery;
import net.jojoaddison.xmserv.domain.Media;
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

	private static final int MAX_DIRECTORY_LENGTH = 16;
	
	private String GALLERY_PHOTOS = "/app/entities/gallery/photos";
	
	private String ALBUM_PHOTOS = "/app/entities/album/photos";


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
        Set<Album> albums = new HashSet<>();
        int i =0;
        if(gallery.getAlbums() != null){
            for(Album album: gallery.getAlbums()){
            	i++;
        		String id = String.valueOf(Double.valueOf(Tools.getDate(null)) + i);
        		album.setId(id);
        		album = createAlbumImage(album);
        		album = createMedia(album);
            	albums.add(album);
            }
            gallery.setAlbums(albums);
        }
        Gallery result = galleryRepository.save(createGalleryImage(gallery));
        return result;
    }

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
			log.info("converting: {}", gallery);
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
					Tools.setPermissions(root.concat(GALLERY_PHOTOS), Tools.getReadPermissions());
					if(gallery.getPictureUrl() != null){
						Tools.removeFile(root.concat(gallery.getPictureUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, gallery.getPicture());
					Tools.setPermission(fileName, Tools.getReadPermissions());
					gallery.setPicture(null);
					gallery.setPictureUrl(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}
    	return gallery;
    }


    private Album createAlbumImage(Album album){
    	if(album.getDirectory()==null){
    		album = setAlbumDirectory(album);
    	}
    	if(album.getPhoto() != null){
			log.info("converting: {}", album);
    		String fileExt = album.getPhotoContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();    		
    		String albumDir = album.getDirectory().concat(sep);
    		String directory = ALBUM_PHOTOS .concat(sep).concat(albumDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
					Tools.setPermissions(root.concat(ALBUM_PHOTOS), Tools.getReadPermissions());
					if(album.getPictureUrl() != null){
						Tools.removeFile(root.concat(album.getPictureUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, album.getPhoto());
					album.setPhoto(null);
					album.setPictureUrl(url);
					Tools.setPermission(fileName, Tools.getReadPermissions());
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}    	
    	}
    	return album;
    	
    }

    private Album setAlbumDirectory(Album album) {		
		String directory = "";
		int i = 0;
		String[] nameParts = album.getName().split("\\s"); 
		do{
			if(i > 0) directory = directory.concat("_");
			directory = directory.concat((nameParts[i]).toLowerCase().trim());
			i++;
		}while(i < nameParts.length && directory.length() < MAX_DIRECTORY_LENGTH);
		
		album.setDirectory(directory);
		
		return album;
	}

	private Album createMedia(Album album) {
    	Set<Media> albumMedia = new HashSet<>();
    	for(Media m: album.getMedia()){
    		albumMedia.add(createMediaItem(m, album));
    	}
    	album.setMedia(albumMedia);
		return album;
	}
    
    private Media createMediaItem(Media media, Album album){
    	if(media.getImage() != null){
    		media = setMediaName(media);
			log.info("converting: {}", media);
    		String fileExt = media.getImageContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String albumDir = album.getDirectory().concat(sep).concat("media").concat(sep);
    		String directory = ALBUM_PHOTOS.concat(sep).concat(albumDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
					Tools.setPermissions(root.concat(ALBUM_PHOTOS), Tools.getReadPermissions());
					if(media.getImageUrl() != null){
						Tools.removeFile(root.concat(media.getImageUrl()));
					}
					log.info("full path: {}", fullPath);
					log.info("image name: {}", media.getFileName());
					String url = directory.concat(sep).concat(media.getFileName()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, media.getImage());
					Tools.setPermission(fileName, Tools.getReadPermissions());
					media.setImage(null);
					media.setImageUrl(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}    	
    	}
    	log.info("media converted: {}", media);
    	return media;
    	
    }
    
    private Media setMediaName(Media media){
    		String mediaName = media.getFileName();
    		if(mediaName != null){
    			mediaName = mediaName.split(".")[0];
    		}
    		if(mediaName == null || mediaName.trim().length() == 0){
    			try{
    			mediaName = media.getCaption().split(".")[0];
    			}catch(Exception e){
    				mediaName = null;
    			}
    		}
    		if(mediaName == null || mediaName.trim().length() == 0){
    			mediaName = media.getId();
    		}
    		media.setFileName(Tools.removeSpaces(mediaName).toLowerCase().concat("_").concat(Tools.getDate()));
    	return media;
    }

}

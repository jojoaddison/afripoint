package net.jojoaddison.xmserv.service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;
import net.jojoaddison.xmserv.domain.Album;
import net.jojoaddison.xmserv.domain.Media;
import net.jojoaddison.xmserv.repository.MediaRepository;
import net.jojoaddison.xmserv.service.util.Tools;

@Service
public class MediaService {

	private Logger log = LoggerFactory.getLogger(MediaService.class);
	
	private final MediaRepository mediaRepository;
	private final Environment env;
	private final String ALBUM_PHOTOS = "data/album/photos";
    private final String DATA = "data/";
    private final int HEIGHT = 120;
    private final int WIDTH = 120;
	
	public MediaService(MediaRepository mediaRepository, Environment env){
		this.mediaRepository = mediaRepository;
		this.env = env;
	}

	public Media save(Media media) {
		media = createMediaItem(media);
		Media result = mediaRepository.save(media);
		return result;
	}


	public Media saveOnly(Media media) {
		Media result = mediaRepository.save(media);
		return result;
	}



	public Page<Media> findAll(Pageable pageable) {
		return mediaRepository.findAll(pageable);
	}




	public Media findOne(String id) {
		return mediaRepository.findOne(id);
	}

	
	public boolean delete(Media media){	
		String root = env.getProperty("client.root");
		String path = root.concat(media.getImageUrl());
		log.debug("path: {}", path);
		return Tools.removeFile(path);
	}


	public void delete(String id) {
		mediaRepository.delete(id);
	}
    	

    public Album handleMedia(Album album){
    	if(album.getMedia() != null && album.getMedia().size() > 0){
        	Set<Media> md = new HashSet<>();
        	for(Media media: album.getMedia()){
        		if(media != null){
            		media.setDirectory(album.getDirectory());
        			if(media.getImage() != null){
                		media = createMediaItem(media); 
                		media = createThumbnail(media); 
        			}
        			md.add(media);
        		}
        	}
        	album.setMedia(md);
        }    	
    	return album;
    }

    public Media createMediaItem(Media media){
    	if(media.getImage() != null){
    		media = setMediaName(media);
			log.info("converting media: {}", media);
    		String fileExt = media.getImageContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String mediaDir = media.getDirectory().concat(sep);
    		String directory = ALBUM_PHOTOS.concat(sep).concat(mediaDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
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
					String thumb = directory.concat(sep).concat(Tools.getDate()).concat("_thumb").concat(".").concat(fileExt).toLowerCase();
					String thumbFileName = root.concat(thumb);
					Thumbnails.of(fileName).size(WIDTH, HEIGHT)
					.outputQuality(0.7)
					.outputFormat(fileExt)
					.toFile(thumbFileName);
					media.setThumbnail(thumb);
					media.setImage(null);
					media.setImageUrl(url);
					Tools.setReadPermissions(root.concat(DATA));
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}    	
        	log.info("media converted: {}", media);
    	}
    	return mediaRepository.save(media);
    	
    }
    

    public Media createThumbnail(Media media){
    	if(media.getThumbnail() == null && media.getImageUrl() != null){
    		String fileExt = media.getImageContentType().split("/")[1].toLowerCase();
    		String photoUrl = media.getImageUrl();
    		String thumbnail = photoUrl.substring(0, photoUrl.lastIndexOf('.')).concat("_thumb").concat(".").concat(fileExt);
    		media.setThumbnail(thumbnail);
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
    	return media;
    }
    

    private Media setMediaName(Media media){
		String mediaName;
		try{ 
			mediaName = media.getFileName();
			log.debug("mediaName 1: {} indexOf(.): {}", mediaName, mediaName.indexOf('.'));
    		if(mediaName != null && mediaName.indexOf('.') > -1){
    			mediaName = mediaName.split(".")[0];
    		}
			log.debug("mediaName 2: {}", mediaName);
    		if(mediaName == null || mediaName.trim().length() == 0 && mediaName.indexOf('.') > -1){
    			mediaName = media.getCaption().split(".")[0];
    			
    		}
			log.debug("mediaName 3: {}", mediaName);
    		if(mediaName == null || mediaName.trim().length() == 0 && mediaName.indexOf('.') > -1){
    			mediaName = media.getId();
    		}
			log.debug("mediaName 4: {}", mediaName);
		}catch(Exception e){
			log.error(e.getMessage(), e.getCause());
			mediaName = media.getId();
		}
		if(mediaName != null && mediaName.length() == 0){
			mediaName = (media.getId()).replaceAll("-", "_");			
		}
		log.debug("mediaName 5: {}", mediaName);
		media.setFileName(Tools.removeSpaces(mediaName).toLowerCase());
    	return media;
    }



}

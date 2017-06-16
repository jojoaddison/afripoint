package net.jojoaddison.xmserv.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.coobird.thumbnailator.Thumbnails;
import net.jojoaddison.xmserv.domain.Album;
import net.jojoaddison.xmserv.repository.AlbumRepository;
import net.jojoaddison.xmserv.service.util.Tools;

@Service
public class AlbumService {
	
	private final Logger log = LoggerFactory.getLogger(AlbumService.class);

	private final AlbumRepository albumRepository;
	private final Environment env;
	
	
	private static final String ALBUM_PHOTOS = "data/album/photos";
	private static final int MAX_DIRECTORY_LENGTH = 16;
    private final String DATA = "data/";
    private final int HEIGHT = 120;
    private final int WIDTH = 120;
	
	
	
	public AlbumService(AlbumRepository albumRepository, Environment env){
		this.albumRepository = albumRepository;
		this.env = env;
	}

	public Page<Album> findAll(Pageable pageable) {
		return albumRepository.findAll(pageable);
	}

	public Album findOne(String id) {
		return albumRepository.findOne(id);
	}

	public void delete(String id) {
		albumRepository.delete(id);
	}
    
	public Album save(Album album){
		log.debug("Request to save an album {}", album);		
		album = createAlbumImage(album);
		Album result = albumRepository.save(album);
		return result;
	}
	
    public Album createAlbumImage(Album album){
    	if(album.getDirectory()==null){
    		album = setAlbumDirectory(album);
    	}
    	if(album.getPhoto() != null){
			log.info("converting album: {}", album);
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
					if(album.getPictureUrl() != null){
						Tools.removeFile(root.concat(album.getPictureUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, album.getPhoto());
					String thumb = directory.concat(sep).concat(Tools.getDate()).concat("_thumb").concat(".").concat(fileExt).toLowerCase();
					String thumbFileName = root.concat(thumb);
					Thumbnails.of(fileName).size(WIDTH, HEIGHT)
					.outputQuality(0.7)
					.outputFormat(fileExt)
					.toFile(thumbFileName);
					album.setThumbnail(thumb);
					album.setPhoto(null);
					album.setPictureUrl(url);
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

	public Album justSave(Album album) {
		return albumRepository.save(album);
	}


    
    
}

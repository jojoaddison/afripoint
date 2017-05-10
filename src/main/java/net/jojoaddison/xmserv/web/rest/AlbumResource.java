package net.jojoaddison.xmserv.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import net.jojoaddison.xmserv.domain.Album;
import net.jojoaddison.xmserv.domain.Media;
import net.jojoaddison.xmserv.repository.AlbumRepository;
import net.jojoaddison.xmserv.service.util.Tools;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Album.
 */
@RestController
@RequestMapping("/api")
public class AlbumResource {

    private final Logger log = LoggerFactory.getLogger(AlbumResource.class);

    private static final String ENTITY_NAME = "album";

	private static final int MAX_DIRECTORY_LENGTH = 16;
        
    private final AlbumRepository albumRepository;

    private final Environment env;
    
	private String ALBUM_PHOTOS = "/app/entities/album/photos";

    public AlbumResource(AlbumRepository albumRepository, Environment env) {
        this.albumRepository = albumRepository;
        this.env = env;
    }

    /**
     * POST  /albums : Create a new album.
     *
     * @param album the album to create
     * @return the ResponseEntity with status 201 (Created) and with body the new album, or with status 400 (Bad Request) if the album has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/albums")
    @Timed
    public ResponseEntity<Album> createAlbum(@Valid @RequestBody Album album) throws URISyntaxException {
        log.debug("REST request to save Album : {}", album);
        if (album.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new album cannot already have an ID")).body(null);
        }
        Album toSave = createAlbumImage(album);
        toSave = createMedia(album);
        Album result = albumRepository.save(toSave);
        return ResponseEntity.created(new URI("/api/albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


	/**
     * PUT  /albums : Updates an existing album.
     *
     * @param album the album to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated album,
     * or with status 400 (Bad Request) if the album is not valid,
     * or with status 500 (Internal Server Error) if the album couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/albums")
    @Timed
    public ResponseEntity<Album> updateAlbum(@Valid @RequestBody Album album) throws URISyntaxException {
        log.debug("REST request to update Album : {}", album);
        if (album.getId() == null) {
            return createAlbum(album);
        }
        Album toSave = createAlbumImage(album);
        toSave = createMedia(album);
        Album result = albumRepository.save(toSave);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, album.getId().toString()))
            .body(result);
    }

    /**
     * GET  /albums : get all the albums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of albums in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/albums")
    @Timed
    public ResponseEntity<List<Album>> getAllAlbums(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Albums");
        Page<Album> page = albumRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/albums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /albums/:id : get the "id" album.
     *
     * @param id the id of the album to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the album, or with status 404 (Not Found)
     */
    @GetMapping("/albums/{id}")
    @Timed
    public ResponseEntity<Album> getAlbum(@PathVariable String id) {
        log.debug("REST request to get Album : {}", id);
        Album album = albumRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(album));
    }

    /**
     * DELETE  /albums/:id : delete the "id" album.
     *
     * @param id the id of the album to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlbum(@PathVariable String id) {
        log.debug("REST request to delete Album : {}", id);
        albumRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
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
    		String albumDir = album.getDirectory();
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
					Tools.setPermission(fileName, Tools.getPermissions775());
					Tools.setPermissions(root.concat(ALBUM_PHOTOS), Tools.getPermissions775());
					album.setPhoto(null);
					album.setPictureUrl(url);
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
    		String directory = ALBUM_PHOTOS .concat(sep).concat(albumDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
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
					String url = directory.concat(sep).concat(media.getFileName()).concat("_").concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, media.getImage());
					Tools.setPermission(fileName, Tools.getPermissions775());
					Tools.setPermissions(root.concat(ALBUM_PHOTOS), Tools.getPermissions775());
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
    		media.setFileName(Tools.removeSpaces(mediaName).toLowerCase());
    	return media;
    }

}

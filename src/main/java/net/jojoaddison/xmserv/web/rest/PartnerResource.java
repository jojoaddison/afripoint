package net.jojoaddison.xmserv.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.jojoaddison.xmserv.domain.Partner;

import net.jojoaddison.xmserv.repository.PartnerRepository;
import net.jojoaddison.xmserv.security.AuthoritiesConstants;
import net.jojoaddison.xmserv.service.util.Tools;
import net.jojoaddison.xmserv.web.rest.util.HeaderUtil;
import net.jojoaddison.xmserv.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Partner.
 */
@RestController
@RequestMapping("/api")
public class PartnerResource {

    private final Logger log = LoggerFactory.getLogger(PartnerResource.class);

    private static final String ENTITY_NAME = "partner";
        
    private final PartnerRepository partnerRepository;
    
    private final Environment env;

    private final String PARTNER_PHOTOS = "/app/entities/partner/photos";

    public PartnerResource(PartnerRepository partnerRepository, Environment env) {
        this.partnerRepository = partnerRepository;
        this.env = env;
    }

    /**
     * POST  /partners : Create a new partner.
     *
     * @param partner the partner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partner, or with status 400 (Bad Request) if the partner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/partners")
    @Timed
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) throws URISyntaxException {
        log.debug("REST request to save Partner : {}", partner);
        if (partner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new partner cannot already have an ID")).body(null);
        }
        
        Partner result = partnerRepository.save(convert(partner));
        return ResponseEntity.created(new URI("/api/partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partners : Updates an existing partner.
     *
     * @param partner the partner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partner,
     * or with status 400 (Bad Request) if the partner is not valid,
     * or with status 500 (Internal Server Error) if the partner couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/partners")
    @Timed
    public ResponseEntity<Partner> updatePartner(@RequestBody Partner partner) throws URISyntaxException {
        log.debug("REST request to update Partner : {}", partner);
        if (partner.getId() == null) {
            return createPartner(partner);
        }
        Partner result = partnerRepository.save(convert(partner));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, partner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partners : get all the partners.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of partners in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/partners")
    @Timed
    public ResponseEntity<List<Partner>> getAllPartners(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Partners");
        Page<Partner> page = partnerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/partners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /partners/:id : get the "id" partner.
     *
     * @param id the id of the partner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partner, or with status 404 (Not Found)
     */
    @GetMapping("/partners/{id}")
    @Timed
    public ResponseEntity<Partner> getPartner(@PathVariable String id) {
        log.debug("REST request to get Partner : {}", id);
        Partner partner = partnerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(partner));
    }

    /**
     * DELETE  /partners/:id : delete the "id" partner.
     *
     * @param id the id of the partner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/partners/{id}")
    @Timed
	@Secured({ AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER })
    public ResponseEntity<Void> deletePartner(@PathVariable String id) {
        log.debug("REST request to delete Partner : {}", id);
        partnerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    private Partner convert(Partner partner){    	
    	if(partner.getImage() != null){
			log.info("converting: {}", partner);
    		String fileExt = partner.getImageContentType().split("/")[1];
    		String root = env.getProperty("client.root");
			log.info("root path: {}", root);
    		String sep = Tools.getSeparator();
    		String partnerDir = Tools.removeSpaces(partner.getFirstname().concat(partner.getLastname()).toLowerCase()).concat(sep);
    		String directory = PARTNER_PHOTOS.concat(sep).concat(partnerDir).concat(Tools.getYear()).concat(sep).concat(Tools.getMonth()).concat(sep).concat(Tools.getDay());
    		String fullPath = root.concat(directory).toLowerCase();
			log.info("before create full path: {}", fullPath);
    		try {
				fullPath = Tools.createDirectory(fullPath);
				if(fullPath != null){
					if(partner.getImageUrl() != null){
						Tools.removeFile(root.concat(partner.getImageUrl()));
					}
					log.info("full path: {}", fullPath);
					String url = directory.concat(sep).concat(Tools.getDate()).concat(".").concat(fileExt).toLowerCase();
					log.info("url path: {}", url);
					String fileName = root.concat(url);
					log.debug("file name {}", fileName);
					Tools.createFile(fileName, partner.getImage());
					Tools.setPermission(fileName, Tools.getPermissions775());
					Tools.setPermissions(root.concat(PARTNER_PHOTOS), Tools.getPermissions775());
					partner.setImage(null);
					partner.setImageUrl(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.getCause());
			}
    	}
    	
    	return partner;
    }
}

package net.jojoaddison.xmserv.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.jojoaddison.xmserv.repository.AfripointServiceRepository;
import net.jojoaddison.xmserv.service.dto.ServiceDTO;

/**
 * Service Implementation for managing Afripoint.
 */
@Service
public class AfripointService {

    private final Logger log = LoggerFactory.getLogger(AfripointService.class);

    private final AfripointServiceRepository afripointServiceRepository;

    public AfripointService(AfripointServiceRepository afripointServiceRepository) {
        this.afripointServiceRepository = afripointServiceRepository;
    }

    public Page<ServiceDTO> findAll(Pageable pageable){
    	log.info("finding all services");
    	return afripointServiceRepository.findAll(pageable).map(ServiceDTO::new);
    }

    public List<ServiceDTO> getAll(){
    	log.info("getting all services");
    	return afripointServiceRepository.findAll().stream().map(ServiceDTO::new).collect(Collectors.toList());
    }
}

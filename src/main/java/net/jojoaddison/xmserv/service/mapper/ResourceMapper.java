package net.jojoaddison.xmserv.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import net.jojoaddison.xmserv.domain.Authority;
import net.jojoaddison.xmserv.domain.Resource;
import net.jojoaddison.xmserv.service.dto.ResourceDTO;

/**
 * Mapper for the entity Resource and its DTO ResourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResourceMapper {

    ResourceDTO resourceToResourceDTO(Resource resource);

    Set<ResourceDTO> resourcesToResourceDTOs(Set<Resource> resources);

    default Resource resourceDTOToResource(ResourceDTO resourceDTO, Authority auth) {
    	Resource res = new Resource();
        res.setId(resourceDTO.getId());
        res.setName(resourceDTO.getName());
        res.setPermission(resourceDTO.getPermission());
        res.setAuthority(auth);

        return res;
    }

    default Set<Resource> resoursesFromResourceDTOs(Set<ResourceDTO> resourceDTOs, Authority auth) {
        return resourceDTOs.stream().map(resourceDTO -> {
            return resourceDTOToResource(resourceDTO, auth);
        }).collect(Collectors.toSet());
    }
}

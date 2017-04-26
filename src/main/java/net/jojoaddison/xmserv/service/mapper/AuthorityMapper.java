package net.jojoaddison.xmserv.service.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;

import net.jojoaddison.xmserv.domain.Authority;
import net.jojoaddison.xmserv.domain.Resource;
import net.jojoaddison.xmserv.service.dto.AuthorityDTO;
import net.jojoaddison.xmserv.service.dto.ResourceDTO;

/**
 * Mapper for the entity Authority and its DTO AuthorityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper {

    AuthorityDTO authorityToAuthorityDTO(Authority authority);

    List<AuthorityDTO> authoritiesToAuthorityDTOs(List<Authority> authorities);

    List<Authority> authorityDTOsToAuthorities(List<AuthorityDTO> authorityDTOs);

    ResourceDTO resourceToResourceDTO(Resource resource);

    Set<ResourceDTO> resourcesToResourceDTOs(Set<Resource> resources);

    default Authority authorityDTOToAuthority(AuthorityDTO authorityDTO) {
    	Authority auth = new Authority();
    	auth.setName(authorityDTO.getName());
    	auth.setResources(resoursesFromResourceDTOs(authorityDTO.getResources(), auth));

    	return auth;
    }

    default Set<Resource> resoursesFromResourceDTOs(Set<ResourceDTO> resourceDTOs, Authority auth) {
        return resourceDTOs.stream().map(resourceDTO -> {
            return resourceDTOToResource(resourceDTO, auth);
        }).collect(Collectors.toSet());
    }

    default Resource resourceDTOToResource(ResourceDTO resourceDTO, Authority auth) {
    	Resource res = new Resource();
        res.setId(resourceDTO.getId());
        res.setName(resourceDTO.getName());
        res.setPermission(resourceDTO.getPermission());
        res.setAuthority(auth);

        return res;
    }
}

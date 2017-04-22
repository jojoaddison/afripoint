package net.jojoaddison.xmserv.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import net.jojoaddison.xmserv.domain.Resource;

public class CustomUserDetails extends User {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Set<Resource> resources;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Set<Resource> resources) {
        super(username, password, authorities);
        this.resources = resources;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
}

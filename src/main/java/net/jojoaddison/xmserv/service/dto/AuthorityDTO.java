package net.jojoaddison.xmserv.service.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO for the Authority entity.
 */
public class AuthorityDTO implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
    @Size(min = 0, max = 50)
    private String name;

    private Set<ResourceDTO> resources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceDTO> resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorityDTO authorityDTO = (AuthorityDTO) o;

        if ( ! Objects.equals(name, authorityDTO.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "AuthorityDTO{" +
            ", name='" + name + "'" +
            '}';
    }
}

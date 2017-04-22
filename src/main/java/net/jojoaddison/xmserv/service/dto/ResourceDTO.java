package net.jojoaddison.xmserv.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the Resource entity.
 */
public class ResourceDTO implements Serializable{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private String id;

    @NotNull
    @Size(min = 0, max = 100)
    private String name;

    @NotNull
    private int permission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String resourceName) {
        this.name = resourceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;

        if ( ! Objects.equals(name, resourceDTO.name)) return false;

        if ( ! Objects.equals(permission, resourceDTO.permission)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + id +
            ", resourceName='" + name + '\'' +
            ", permission=" + permission +
            '}';
    }
}

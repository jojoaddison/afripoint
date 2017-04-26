package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "jhi_resource")
public class Resource implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(value="name")
	private String name;

    @Field(value="description")
	private String description;

    @Field(value="permission")
	private int permission;

	@JsonIgnore
	private Authority authority;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public Resource authority(Authority authority){
		this.authority = authority;
		return this;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
    	return Objects.hashCode(id);
    }

    public String toString(){
    	return "Resource{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                '}';
    }

}

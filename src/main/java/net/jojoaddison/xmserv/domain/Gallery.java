package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import net.jojoaddison.xmserv.service.dto.AuditUserDTO;

/**
 * A Gallery.
 */

@Document(collection = "gallery")
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 2, max = 256)
    @Field("name")
    private String name;

    @Field("picture_url")
    private String pictureUrl;
    
    @Field("picture")
    private byte[] picture;

    @Field("picture_content_type")
    private String pictureContentType;

    @Field("albums")
    private Set<Album> albums;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("created_by")
    private AuditUserDTO createdBy;

    @Field("modified_by")
    private AuditUserDTO modifiedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Gallery name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
		return pictureUrl;
	}
    
    public Gallery pictureUrl(String pictureUrl){
    	this.pictureUrl = pictureUrl;
    	return this;
    }
    	
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public byte[] getPicture() {
        return picture;
    }

    public Gallery picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Gallery pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public Gallery albums(Set<Album> albums) {
        this.albums = albums;
        return this;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Gallery createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Gallery modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public AuditUserDTO getCreatedBy() {
        return createdBy;
    }

    public Gallery createdBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public AuditUserDTO getModifiedBy() {
        return modifiedBy;
    }

    public Gallery modifiedBy(AuditUserDTO modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(AuditUserDTO modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gallery gallery = (Gallery) o;
        if (gallery.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gallery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gallery{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", pictureContentType='" + pictureContentType + "'" +
            ", album='" + albums + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", modifiedBy='" + modifiedBy + "'" +
            '}';
    }
}

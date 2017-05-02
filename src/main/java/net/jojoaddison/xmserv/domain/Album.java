package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import net.jojoaddison.xmserv.service.dto.AuditUserDTO;

/**
 * A Album.
 */

@Document(collection = "album")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("created_by")
    private AuditUserDTO createdBy;

    @Field("modified_by")
    private AuditUserDTO modifiedBy;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("media")
    private Set<Media> media;

    @Field("picture_url")
    private String pictureUrl;
    
    @Field("photo")
    private byte[] photo;

    @Field("photo_content_type")
    private String photoContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Album name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
		return pictureUrl;
	}

    public Album pictureUrl(String pictureUrl){
    	this.pictureUrl = pictureUrl;
    	return this;
    }
    
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getDescription() {
        return description;
    }

    public Album description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuditUserDTO getCreatedBy() {
        return createdBy;
    }

    public Album createdBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public AuditUserDTO getModifiedBy() {
        return modifiedBy;
    }

    public Album modifiedBy(AuditUserDTO modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(AuditUserDTO modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Album createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Album modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public Album media(Set<Media> media) {
        this.media = media;
        return this;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Album photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Album photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Album album = (Album) o;
        if (album.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Album{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", createdBy='" + createdBy + "'" +
            ", modifiedBy='" + modifiedBy + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", media='" + media + "'" +
            ", photo='" + photo + "'" +
            ", photoContentType='" + photoContentType + "'" +
            '}';
    }
}

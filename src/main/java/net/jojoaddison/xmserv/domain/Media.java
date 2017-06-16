package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Media.
 */

@Document(collection = "media")
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("caption")
    private String caption;

    @Field("image_url")
    private String imageUrl;
    
    @Field("thumbnail")
    private String thumbnail;

    @Field("image")
    private byte[] image;

    @Field("image_content_type")
    private String imageContentType;

    @Field("description")
    private String description;
    
    @Field("directory")
    private String directory;
    
    @Field("fileName")
    private String fileName;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public Media caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Media imageUrl(String imageUrl){
		this.imageUrl = imageUrl;
		return this;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public byte[] getImage() {
        return image;
    }

    public Media image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Media imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
        return description;
    }

    public Media description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectory() {
		return directory;
	}
    
    public Media directory(String directory){
    	this.directory = directory;
    	return this;
    }

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Media createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Media modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Media media = (Media) o;
        if (media.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, media.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Media{" +
            "id=" + id +
            ", caption='" + caption + "'" +
            ", imageUrl='" + imageUrl + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", description='" + description + "'" +
            ", directory='" + directory + "'" +
            ", fileName='" + fileName + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            '}';
    }
}

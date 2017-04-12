package net.jojoaddison.xmserv.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

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

    @Field("url")
    private String url;

    @Field("name")
    private String name;

    @Field("bytes")
    private byte[] bytes;

    @Field("bytes_content_type")
    private String bytesContentType;

    @Field("description")
    private String description;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("type")
    private String type;

    @Field("size")
    private Long size;

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

    public String getUrl() {
        return url;
    }

    public Media url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Media name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Media bytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getBytesContentType() {
        return bytesContentType;
    }

    public Media bytesContentType(String bytesContentType) {
        this.bytesContentType = bytesContentType;
        return this;
    }

    public void setBytesContentType(String bytesContentType) {
        this.bytesContentType = bytesContentType;
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

    public String getType() {
        return type;
    }

    public Media type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public Media size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
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
            ", url='" + url + "'" +
            ", name='" + name + "'" +
            ", bytes='" + bytes + "'" +
            ", bytesContentType='" + bytesContentType + "'" +
            ", description='" + description + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", type='" + type + "'" +
            ", size='" + size + "'" +
            '}';
    }
}

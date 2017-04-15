package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Event.
 */

@Document(collection = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;
    
    @Field("gate_fee")
    private double gateFee;

    @Field("owner")
    private Partner owner;

    @Field("description")
    private String description;

    @Field("photo")
    private String photo;

    @Field("image")
    private byte[] image;

    @Field("image_content_type")
    private String imageContentType;
    
    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("created_by")
    private User createdBy;

    @Field("modified_by")
    private User modifiedBy;

    @Field("start_time")
    private ZonedDateTime startTime;

    @NotNull
    @Field("end_time")
    private ZonedDateTime endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Event title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getGateFee() {
        return gateFee;
    }

    public Event gateFee(Double gateFee) {
        this.gateFee = gateFee;
        return this;
    }

    public void setGateFee(Double gateFee) {
        this.gateFee = gateFee;
    }

    public Partner getOwner() {
        return owner;
    }

    public Event owner(Partner owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Partner owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public Event photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Event createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Event modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Event createdBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public Event modifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Event startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Event endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public byte[] getImage() {
        return image;
    }

    public Event image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Event imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        if (event.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", owner='" + owner + "'" +
            ", description='" + description + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", modifiedBy='" + modifiedBy + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            '}';
    }
}

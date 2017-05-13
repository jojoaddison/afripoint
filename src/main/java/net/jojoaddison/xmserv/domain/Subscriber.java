package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Subscriber.
 */

@Document(collection = "subscriber")
public class Subscriber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("email")
    private String email;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("active")
    private Boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Subscriber email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Subscriber createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isActive() {
        return active;
    }

    public Subscriber active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subscriber subscriber = (Subscriber) o;
        if (subscriber.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subscriber.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subscriber{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", createdDate='" + createdDate + "'" +
            ", active='" + active + "'" +
            '}';
    }
}

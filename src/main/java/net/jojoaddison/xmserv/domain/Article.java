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
 * A Article.
 */

@Document(collection = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("aid")
    private String aid;

    @Field("pages")
    private Set<Page> pages;

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

    public String getAid() {
        return aid;
    }

    public Article aid(String aid) {
        this.aid = aid;
        return this;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public Article pages(Set<Page> pages) {
        this.pages = pages;
        return this;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Article createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Article modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public AuditUserDTO getCreatedBy() {
        return createdBy;
    }

    public Article createdBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public AuditUserDTO getModifiedBy() {
        return modifiedBy;
    }

    public Article modifiedBy(AuditUserDTO modifiedBy) {
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
        Article article = (Article) o;
        if (article.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", aid='" + aid + "'" +
            ", pages='" + pages + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", modifiedBy='" + modifiedBy + "'" +
            '}';
    }
}

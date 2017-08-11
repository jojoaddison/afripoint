package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import net.jojoaddison.xmserv.service.dto.AuditUserDTO;

@Document(collection = "event_documents")
public class EventDocument  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1139120765152251072L;

    @Id
	private String id;

    @Field("title")
	private String title;

    @Field("src")
	private String src; 

    @Field("name")
	private String name;  

    @Field("content_type")
	private String contentType;   

    @Field("version")
	private long version;	

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("created_by")
    private AuditUserDTO createdBy;

    @Field("modified_by")
    private AuditUserDTO modifiedBy;
    
    @JsonIgnore
    private byte[] document;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}	

	public byte[] getDocument() {
		return document;
	}	

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public EventDocument createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public EventDocument modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public AuditUserDTO getCreatedBy() {
        return createdBy;
    }

    public EventDocument createdBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(AuditUserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public AuditUserDTO getModifiedBy() {
        return modifiedBy;
    }

    public EventDocument modifiedBy(AuditUserDTO modifiedBy) {
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
        EventDocument eventDocument = (EventDocument) o;
        if (eventDocument.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eventDocument.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}

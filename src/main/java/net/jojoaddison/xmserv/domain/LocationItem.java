package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
import net.jojoaddison.xmserv.domain.enumeration.Periods;

/**
 * A LocationOrder.
 */

@Document(collection = "location_service")
public class LocationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("category")
    private String category;

    @Field("service")
    private String service;

    @Field("description")
    private String description;

    @Field("period")
    private Periods period;

    @Field("price")
    private Double price;

    @Field("member_price")
    private Double memberPrice;
<<<<<<< HEAD
    
    @Field("album")
    private String album;
    
    @Field("image_url")
    private String imageUrl;
    
    @Field("thumbnail")
    private String thumbnail;
    
    @Field("content_type")
    private String contentType;
    
    private byte[] image;
=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public LocationItem category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getService() {
        return service;
    }

    public LocationItem service(String service) {
        this.service = service;
        return this;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public LocationItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Periods getPeriod() {
        return period;
    }

    public LocationItem period(Periods period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Periods period) {
        this.period = period;
    }

    public Double getPrice() {
        return price;
    }

    public LocationItem price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
	}
	
	public LocationItem memberPrice(Double price){
		this.memberPrice = price;
		return this;
	}

<<<<<<< HEAD
	public String getAlbum() {
		return album;
	}
	
	public LocationItem album(String album){
		this.album = album;
		return this;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

=======
>>>>>>> 82dfbbffc7b4bbb5a6912aac616945f36895b866
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationItem locationOrder = (LocationItem) o;
        if (locationOrder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, locationOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocationOrder{" +
            "id=" + id +
            ", category='" + category + "'" +
            ", service='" + service + "'" +
            ", description='" + description + "'" +
            ", period='" + period + "'" +
            ", price='" + price + "'" +
            '}';
    }
}

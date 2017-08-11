package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Partner.
 */

@Document(collection = "partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("firstname")
    private String firstname;

    @Field("lastname")
    private String lastname;

    @Field("title")
    private String title;

    @Field("type")
    private String type;

    @Field("email")
    private String email;

    @Field("mobile_number")
    private String mobileNumber;

    @Field("telephone_number")
    private String telephoneNumber;

    @Field("street_address")
    private String streetAddress;

    @Field("zipcode")
    private String zipcode;

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @Field("country")
    private String country;

    @Field("region")
    private String region;

    @Field("continent")
    private String continent;

    @Field("notes")
    private String notes;
    
    @Field("image_url")
    private String imageUrl;

    @Field("image")
    private byte[] image;

    @Field("image_content_type")
    private String imageContentType;

    @Field("thumbnail")
    private String thumbnail;
    
    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("created_by")
    private User createdBy;

    @Field("modified_by")
    private User modifiedBy;

   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public Partner firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Partner lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public Partner title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
		return type;
	}
    
    public Partner type(String type){
    	this.type = type;
    	return this;
    }

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
        return email;
    }

    public Partner email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Partner mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public Partner telephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Partner streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Partner zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public Partner city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Partner state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public Partner country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public Partner region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContinent() {
        return continent;
    }

    public Partner continent(String continent) {
        this.continent = continent;
        return this;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	 public ZonedDateTime getCreatedDate() {
	        return createdDate;
	    }

	    public Partner createdDate(ZonedDateTime createdDate) {
	        this.createdDate = createdDate;
	        return this;
	    }

	    public void setCreatedDate(ZonedDateTime createdDate) {
	        this.createdDate = createdDate;
	    }

	    public ZonedDateTime getModifiedDate() {
	        return modifiedDate;
	    }

	    public Partner modifiedDate(ZonedDateTime modifiedDate) {
	        this.modifiedDate = modifiedDate;
	        return this;
	    }

	    public void setModifiedDate(ZonedDateTime modifiedDate) {
	        this.modifiedDate = modifiedDate;
	    }

	    public User getCreatedBy() {
	        return createdBy;
	    }

	    public Partner createdBy(User createdBy) {
	        this.createdBy = createdBy;
	        return this;
	    }

	    public void setCreatedBy(User createdBy) {
	        this.createdBy = createdBy;
	    }

	    public User getModifiedBy() {
	        return modifiedBy;
	    }

	    public Partner modifiedBy(User modifiedBy) {
	        this.modifiedBy = modifiedBy;
	        return this;
	    }

	    public void setModifiedBy(User modifiedBy) {
	        this.modifiedBy = modifiedBy;
	    }


	    public byte[] getImage() {
	        return image;
	    }

	    public Partner image(byte[] image) {
	        this.image = image;
	        return this;
	    }

	    public void setImage(byte[] image) {
	        this.image = image;
	    }

	    public String getImageContentType() {
	        return imageContentType;
	    }

	    public Partner imageContentType(String imageContentType) {
	        this.imageContentType = imageContentType;
	        return this;
	    }

	    public void setImageContentType(String imageContentType) {
	        this.imageContentType = imageContentType;
	    }

	    
	    public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		
		public Partner imageUrl(String imageUrl){
			this.imageUrl = imageUrl;
			return this;
		}

		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		
		public Partner thumbnail(String thumbnail){
			this.thumbnail = thumbnail;
			return this;
		}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Partner partner = (Partner) o;
        if (partner.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, partner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partner{" +
            "id=" + id +
            ", firstname='" + firstname + "'" +
            ", lastname='" + lastname + "'" +
            ", title='" + title + "'" +
            ", email='" + email + "'" +
            ", mobileNumber='" + mobileNumber + "'" +
            ", telephoneNumber='" + telephoneNumber + "'" +
            ", streetAddress='" + streetAddress + "'" +
            ", zipcode='" + zipcode + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", country='" + country + "'" +
            ", region='" + region + "'" +
            ", continent='" + continent + "'" +
            '}';
    }

}

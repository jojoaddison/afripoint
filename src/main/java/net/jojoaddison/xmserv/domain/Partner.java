package net.jojoaddison.xmserv.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

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

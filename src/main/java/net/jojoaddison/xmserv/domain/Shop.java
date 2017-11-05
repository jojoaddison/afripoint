package net.jojoaddison.xmserv.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import net.jojoaddison.xmserv.domain.enumeration.ShopCategory;

/**
 * A Shop.
 */

@Document(collection = "shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("contact")
    private String contact;

    @Field("products")
    private Set<Product> products = new HashSet<>();

    @Field("address")
    private String address;

    @Field("email")
    private String email;

    @Field("telephone")
    private Set<String> telephone = new HashSet<>();

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("categories")
    private Set<ProductCategory> categories = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Shop name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public Shop contact(String owner) {
        this.contact = owner;
        return this;
    }

    public void setContact(String owner) {
        this.contact = owner;
    }

    public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public String getAddress() {
        return address;
    }

    public Shop address(String physicalAddress) {
        this.address = physicalAddress;
        return this;
    }

    public void setAddress(String physicalAddress) {
        this.address = physicalAddress;
    }

    public String getEmail() {
        return email;
    }

    public Shop email(String virtualAddress) {
        this.email = virtualAddress;
        return this;
    }

    public void setEmail(String virtualAddress) {
        this.email = virtualAddress;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Shop createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Shop modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shop shop = (Shop) o;
        if (shop.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, shop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shop{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", owner='" + contact + "'" +
            ", products='" + products + "'" +
            ", physicalAddress='" + address + "'" +
            ", virtualAddress='" + email + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", category='" + categories + "'" +
            '}';
    }
}

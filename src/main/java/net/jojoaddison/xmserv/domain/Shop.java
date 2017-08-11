package net.jojoaddison.xmserv.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

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

    @Field("owner")
    private String owner;

    @Field("products")
    private String products;

    @Field("physical_address")
    private String physicalAddress;

    @Field("virtual_address")
    private String virtualAddress;

    @Field("created_date")
    private ZonedDateTime createdDate;

    @Field("modified_date")
    private ZonedDateTime modifiedDate;

    @Field("category")
    private ShopCategory category;

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

    public String getOwner() {
        return owner;
    }

    public Shop owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProducts() {
        return products;
    }

    public Shop products(String products) {
        this.products = products;
        return this;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public Shop physicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
        return this;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getVirtualAddress() {
        return virtualAddress;
    }

    public Shop virtualAddress(String virtualAddress) {
        this.virtualAddress = virtualAddress;
        return this;
    }

    public void setVirtualAddress(String virtualAddress) {
        this.virtualAddress = virtualAddress;
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

    public ShopCategory getCategory() {
        return category;
    }

    public Shop category(ShopCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(ShopCategory category) {
        this.category = category;
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
            ", owner='" + owner + "'" +
            ", products='" + products + "'" +
            ", physicalAddress='" + physicalAddress + "'" +
            ", virtualAddress='" + virtualAddress + "'" +
            ", createdDate='" + createdDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", category='" + category + "'" +
            '}';
    }
}

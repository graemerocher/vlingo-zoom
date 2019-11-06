package io.examples.account.domain;

import io.examples.account.data.BaseEntity;

import javax.persistence.*;

/**
 * This is a simple {@link Address} value object and entity.
 *
 * @author Kenny Bastani
 * @see Account
 */
@Entity
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street1;
    private String street2;
    private String state;
    private String city;
    private String country;
    private Integer zipCode;
    private AddressType type;

    /**
     * Instantiates a new {@link Address} entity.
     */
    public Address() {
    }

    /**
     * Instantiates a new {@link Address} entity with overloaded arguments.
     */
    public Address(String street1, String street2, String state, String city, String country, AddressType type,
                   Integer zipCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.state = state;
        this.city = city;
        this.country = country;
        this.type = type;
        this.zipCode = zipCode;
    }

    /**
     * Get the {@link Address} entity's unique identifier.
     *
     * @return a unique identifier for the address.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the {@link Address} identity for this entity.
     *
     * @param id is a unique identifier for the address.
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street1='" + street1 + '\'' +
                ", street2='" + street2 + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode=" + zipCode +
                ", type=" + type +
                '}';
    }

    /**
     * The {@link AddressType} represents the type of address that is assigned to an {@link Account}.
     *
     * @author Kenny Bastani
     * @see Account
     * @see Address
     */
    public enum AddressType {
        SHIPPING,
        BILLING
    }
}

package io.examples.account.domain;

import io.examples.account.data.BaseEntity;

import javax.persistence.*;

/**
 * A {@link CreditCard} belonging to a customer {@link Account} is used for processing payments.
 *
 * @author Kenny Bastani
 * @see Account
 * @see CreditCardType
 */
@Entity
public class CreditCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String number;
    private CreditCardType type;

    public CreditCard() {
    }

    public CreditCard(String number, CreditCardType type) {
        this.number = number;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", type=" + type +
                '}';
    }

    /**
     * The {@link CreditCardType} is used to categorized the provider of a {@link CreditCard} for processing payments.
     *
     * @author Kenny Bastani
     * @see CreditCard
     */
    public enum CreditCardType {
        VISA,
        MASTERCARD,
        AMERICAN_EXPRESS
    }
}

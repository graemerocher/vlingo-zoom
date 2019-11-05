package io.examples.account.domain;

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

package io.examples;

import io.micronaut.runtime.Micronaut;

/**
 * The {@link AccountApplication} is a microservice that implements features in the Account context.
 */
public class AccountApplication {

    public static void main(String[] args) {
        Micronaut.run(AccountApplication.class);
    }
}

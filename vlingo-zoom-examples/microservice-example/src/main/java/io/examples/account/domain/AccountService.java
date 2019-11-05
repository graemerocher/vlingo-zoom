package io.examples.account.domain;

import io.examples.account.endpoint.AccountEndpoint;
import io.examples.account.repository.AccountRepository;

import javax.inject.Singleton;
import java.util.List;

/**
 * The {@link AccountService} exposes operations and business logic that pertains to the {@link Account} entity and
 * aggregate. This service forms an anti-corruption layer that is exposed by the {@link AccountEndpoint}.
 *
 * @author Kenny Bastani
 */
@Singleton
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Get all {@link Account}s from the {@link AccountRepository}.
     *
     * @return a list of {@link Account} entities.
     */
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Lookup an {@link Account} by its unique identifier.
     *
     * @param id is the unique identifier of the {@link Account}.
     * @return the {@link Account} or throw a {@link RuntimeException} if one does not exist.
     */
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Account with id[" + id + "] does not exist"));
    }

    /**
     * Register a new {@link Account}.
     *
     * @param account is the {@link Account} to create.
     * @return the created {@link Account}.
     */
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Updates the {@link Account} with the corresponding unique identifier.
     *
     * @param id      is the unique identifier for the {@link Account}.
     * @param account is the {@link Account} entity containing the fields to update.
     * @return the updated {@link Account}.
     */
    public Account updateAccount(Long id, Account account) {
        accountRepository.update(id, account);
        return getAccount(id);
    }

    /**
     * Delete the {@link Account} with the corresponding unique identifier.
     *
     * @param id is the unique identifier of the {@link Account} that is to be deleted.
     */
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}

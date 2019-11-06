package io.examples.account.domain.context;

import io.examples.account.domain.Account;
import io.examples.account.repository.AccountRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The {@link AccountService} represents the core business logic of this application as it applies to the
 * {@link Account} aggregate.
 *
 * @author Kenny Bastani
 * @see AccountContext
 */
public interface AccountService {

    /**
     * Get all {@link Account}s from the {@link AccountRepository}.
     *
     * @return a list of {@link Account} entities.
     */
    List<Account> getAccounts();

    /**
     * Lookup an {@link Account} by its unique identifier.
     *
     * @param id is the unique identifier of the {@link Account}.
     * @return the {@link Account} or throw a {@link RuntimeException} if one does not exist.
     */
    Account getAccount(Long id);

    /**
     * Register a new {@link Account}.
     *
     * @param account is the {@link Account} to create.
     * @return the created {@link Account}.
     */
    Account createAccount(Account account);

    /**
     * Updates the {@link Account} with the corresponding unique identifier.
     *
     * @param id      is the unique identifier for the {@link Account}.
     * @param account is the {@link Account} entity containing the fields to update.
     * @return the updated {@link Account}.
     */
    Account updateAccount(@NotNull Long id, @NotNull Account account);

    /**
     * Delete the {@link Account} with the corresponding unique identifier.
     *
     * @param id is the unique identifier of the {@link Account} that is to be deleted.
     */
    void deleteAccount(Long id);
}

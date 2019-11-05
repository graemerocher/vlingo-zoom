package io.examples.account.repository;

import io.examples.account.data.JpaRepository;
import io.examples.account.domain.Account;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * The {@link AccountRepository} provides a persistence context for managing data with Hibernate/JPA.
 *
 * @author Kenny Bastani
 */
@Singleton
public class AccountRepository implements JpaRepository<Long, Account> {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Instantiates a {@link JpaRepository} with a dependency-injected {@link EntityManager}.
     *
     * @param entityManager is a JPA {@link EntityManager}.
     */
    public AccountRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Find an entity by its {@link Account}.
     *
     * @param id is the {@link Long} of the entity to retrieve.
     * @return the entity {@link Account} or returns null.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Account.class, id));
    }

    /**
     * Saves the entity of type {@link Account}.
     *
     * @param account is the entity of type {@link Account} to save.
     * @return the saved entity of type {@link Account}.
     */
    @Override
    @Transactional
    public Account save(@NotNull Account account) {
        account = entityManager.merge(account);
        entityManager.persist(account);
        return account;
    }

    /**
     * Deletes an entity of type {@link Account} with the unique identifier of type {@link Long}.
     *
     * @param id is the entity identifier of type {@link Long}.
     */
    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        Optional.of(findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account with id[" + id + "] does not exist")))
                .ifPresent(account -> entityManager.remove(account));


    }

    /**
     * Returns a list of all entities of type {@link Account}.
     *
     * @return a list of entities of type {@link Account}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        String qlString = "SELECT a FROM Account as a";
        TypedQuery<Account> query = entityManager.createQuery(qlString, Account.class);
        return query.getResultList();
    }

    /**
     * Updates fields belonging to the entity of type {@link Account}.
     *
     * @param id      is the entity identifier of type {@link Long} to lookup before updating any its fields.
     * @param account is the entity model containing the fields other than its identifier that will be updated.
     * @return the updated {@link Account} entity.
     */
    @Override
    @Transactional
    public Account update(@NotNull Long id, @NotNull Account account) {
        account.setId(id);
        return save(account);
    }
}

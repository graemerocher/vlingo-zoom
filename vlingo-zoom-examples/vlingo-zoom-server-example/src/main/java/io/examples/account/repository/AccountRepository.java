package io.examples.account.repository;

import io.examples.account.data.JpaRepository;
import io.examples.account.domain.Account;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
public class AccountRepository implements JpaRepository<Long, Account> {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;

    public AccountRepository(@CurrentSession EntityManager entityManager,
                             ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Account.class, id));
    }

    @Override
    @Transactional
    public Account save(@NotNull Account account) {
        account = entityManager.merge(account);
        entityManager.persist(account);
        return account;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        Optional.of(findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account with id[" + id + "] does not exist")))
                .ifPresent(account -> entityManager.remove(account));


    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        String qlString = "SELECT a FROM Account as a";
        TypedQuery<Account> query = entityManager.createQuery(qlString, Account.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int update(@NotNull Long id, @NotNull Account account) {
        return entityManager.createQuery("UPDATE Account a SET accountNumber = :accountNumber where id = :id")
                .setParameter("id", account.getId())
                .setParameter("accountNumber", account.getAccountNumber())
                .executeUpdate();
    }
}

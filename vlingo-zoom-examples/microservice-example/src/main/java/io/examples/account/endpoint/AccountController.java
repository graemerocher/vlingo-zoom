package io.examples.account.endpoint;

import io.examples.account.domain.Account;
import io.examples.account.repository.AccountRepository;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Account with id[" + id + "] does not exist"));
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account account) {
        accountRepository.update(id, account);
        return getAccount(id);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}

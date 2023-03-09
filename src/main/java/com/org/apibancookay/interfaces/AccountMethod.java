package com.org.apibancookay.interfaces;

import java.util.Collection;

import com.org.apibancookay.models.Account;

public interface AccountMethod {
	Collection<Account> getAccounts();
	Account getAccountById(Long id);
	Account getAccountByCpfAndPassword(String cpf, String password);
	String createAccount(Account account);
	Account updateAccount(Long id, Account account);
	Account deleteAccountById(Long id);
}

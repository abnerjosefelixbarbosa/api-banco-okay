package com.org.apibancookay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.repositories.AccountRepository;

@Service
public class AccountService implements AccountMethod {
	@Autowired
	private AccountRepository accountRepository;

	public Account findByAccountId(Long accountId) {
		return accountRepository.findByAccountId(accountId).orElse(null);
	}
	
	public Account findByAgencyAndAccount(String agency, String account) {
		return accountRepository.findByAgencyAndAccount(agency, account).orElse(null);
	}
	
	public Account findById(Long id) {
		return accountRepository.findById(id).orElse(null);
	}
	
	public Account save(Account account) {
		return accountRepository.save(account);
	}
}

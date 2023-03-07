package com.org.apibancookay.services;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.repositories.AccountRepository;
import com.org.apibancookay.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService implements AccountMethod {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;

	public Collection<Account> getAccounts() {
		return accountRepository.findAll();
	}

	public Account getAccountById(Long id) {
		if (!accountRepository.existsById(id)) {
			return null;
		}
		
		return accountRepository.findById(id).get();
	}

	@Transactional
	public String createAccount(Account account) {
		if (!customerRepository.existsById(account.getCustomer().getId())) {
			return "customer not found";
		}
		if (!accountRepository.existsById(account.getId())) {
			accountRepository.save(account);
			return "account created";
		}
		
		return "account not created";
	}

	@Transactional
	public Account updateAccount(Long id, Account account) {
		if (!accountRepository.existsById(id)) {
			return null;
		}
		
		Account accountFound = accountRepository.findById(id).get();
		BeanUtils.copyProperties(account, accountFound);	
		return accountRepository.save(accountFound);
	}

	@Transactional
	public Account deleteAccountById(Long id) {
		if (!accountRepository.existsById(id)) {
			return null;
		}
		
		accountRepository.deleteById(id);
		return new Account();
	}

}

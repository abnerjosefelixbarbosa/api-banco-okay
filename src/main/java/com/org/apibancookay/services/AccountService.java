package com.org.apibancookay.services;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;
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
			return "cliente não encontrado";
		}
		if (accountRepository.existsByCustomerId(account.getCustomer().getId())) {
			return "cliente já registrado";
		}
		Customer customerFound = customerRepository.findById(account.getCustomer().getId()).get();
		if (!accountRepository.existsById(account.getId())) {
			account.setCustomer(customerFound);
			accountRepository.save(account);
			return "conta criada";
		}
		
		return "conta não criada";
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

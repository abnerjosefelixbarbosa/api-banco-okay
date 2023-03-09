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
	
	public Account getAccountByCpfAndPassword(String cpf, String password) {
		if (!customerRepository.existsByCpfAndPassword(cpf, password)) {
			return null;
		}
		
		Customer customerFound = customerRepository.findByCpfAndPassword(cpf, password).get();
		return accountRepository.findById(customerFound.getId()).get();
	}

	@Transactional
	public String createAccount(Account account) {
		if (!customerRepository.existsById(account.getCustomer().getId())) {
			return "id do cliente não encontrado";
		}
		if (accountRepository.existsByAgency(account.getAgency())) {
			return "agência já existe";
		}
		if (accountRepository.existsByAccount(account.getAccount())) {
			return "conta já existe";
		}
		if (accountRepository.existsByPassword(account.getPassword())) {
			return "senha já existe";
		}
		if (accountRepository.existsByCustomerId(account.getCustomer().getId())) {
			return "id do cliente já existe";
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
	public String updateAccount(Long id, Account account) {
		if (!accountRepository.existsById(id)) {
			return "id não encontrado";
		}
		if (id != account.getId()) {
			return "id diferente";
		}
		if (accountRepository.existsByAgency(account.getAgency())) {
			return "agência já existe";
		}
		if (accountRepository.existsByAccount(account.getAccount())) {
			return "conta já existe";
		}
		if (accountRepository.existsByPassword(account.getPassword())) {
			return "senha já existe";
		}
		if (accountRepository.existsByCustomerId(account.getCustomer().getId())) {
			return "id do cliente já existe";
		}
		
		Account accountFound = accountRepository.findById(id).get();
		BeanUtils.copyProperties(account, accountFound);
		accountRepository.save(accountFound);	
		return "conta alterada";
	}

	@Transactional
	public String deleteAccountById(Long id) {
		if (!accountRepository.existsById(id)) {
			return "id não encontrado";
		}
		
		accountRepository.deleteById(id);
		return "conta deletada";
	}
}

package com.org.apibancookay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.interfaces.CustomerMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;
import com.org.apibancookay.repositories.AccountRepository;

@Service
public class AccountService implements AccountMethod {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerMethod customerMethod;

	public Account findByCpfAndPassword(String cpf, String password) {
		Customer customer = customerMethod.findByCpfAndPassword(cpf, password);
		if (customer == null) {
			return null;
		}
		
		return accountRepository.findByCustomer(customer).orElse(null);
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

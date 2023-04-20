package com.org.apibancookay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.interfaces.CustomerMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;
import com.org.apibancookay.repositories.AccountRepository;

import br.com.caelum.stella.validation.CPFValidator;

@Service
public class AccountService implements AccountMethod {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerMethod customerMethod;

	public Account loginByCpfAndPassword(Customer customer) throws Exception {
		String checkedAccount = checkLoginByCpfAndPassword(customer);
		if (!checkedAccount.equals("conta valida")) {
			throw new Exception(checkedAccount);
		}

		Customer customerFound = customerMethod.findByCpfAndPassword(customer);
		Account accountFound = accountRepository.findByCustomer(customerFound).orElse(null);
		if (accountFound == null) {
			return null;
		}

		return accountFound;
	}

	private String checkLoginByCpfAndPassword(Customer customer) {
		if (!checkCpf(customer.getCpf())) {
			return "cpf invalido";
		}
		if (customer.getPassword().isEmpty() || customer.getPassword().length() != 6) {
			return "senha invalida";
		}

		return "conta valida";
	}

	private boolean checkCpf(String cpf) {
		CPFValidator cpfValidator = new CPFValidator();
		String newCpf = cpf.replace(".", "").replace("-", "");

		try {
			cpfValidator.assertValid(newCpf);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Account findByAgencyAndAccount(Account account) throws Exception {
		String checkedAccount = checkFindByAgencyAndAccount(account);
		if (!checkedAccount.equals("conta valida")) {
			throw new Exception(checkedAccount);
		}

		Account accountFound = accountRepository.findByAgencyAndAccount(account.getAgency(), account.getAccount()).orElse(null);
		if (accountFound == null) {
			return accountFound;
		}

		return accountFound;
	}

	private String checkFindByAgencyAndAccount(Account account) {
		if (account.getAgency().isEmpty() || account.getAgency().length() != 6) {
			return "agência invalida";
		} 
		if (account.getAccount().isEmpty() || account.getAccount().length() != 7) {
			return "conta invalida";
		}

		return "conta valida";
	}

	public String transferBalance(Long id1, Long id2, Account account) throws Exception {
		if (id1 == id2) {
			throw new Exception("ids iguais");
		}
		
		String balanceChecked = checkTransferBalance(account);
		if (!balanceChecked.equals("saldo valido")) {
			throw new Exception(balanceChecked);
		}

		Account accountFound1 = accountRepository.findById(id1).orElse(null);
		if (accountFound1 == null) {
			return "conta 1 não encontrada";
		}

		Account accountFound2 = accountRepository.findById(id1).orElse(null);
		if (accountFound2 == null) {
			return "conta 2 não encontrada";
		}

		accountFound1.withdraw(account.getBalance());
		accountFound2.deposit(account.getBalance());
		accountRepository.save(accountFound1);
		accountRepository.save(accountFound2);
		return "saldo transferido";
	}

	private String checkTransferBalance(Account account) {
		if (account.getBalance().doubleValue() == 0) {
			return "saldo invalido";
		}

		return "saldo valido";
	}
}

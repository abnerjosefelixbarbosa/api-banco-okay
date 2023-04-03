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

	public Account loginAccountByCpfAndPassword(Customer customer) throws Exception {
		String checkedAccount = checkLoginAccountByCpfAndPassword(customer);
		if (!checkedAccount.equals("conta verificada"))
			throw new Exception(checkedAccount);

		Customer customerFound = customerMethod.findByCpfAndPassword(customer);
		Account accountFound = accountRepository.findByCustomer(customerFound).orElse(null);
		if (accountFound == null)
			throw new Exception("conta não encontrada");

		return accountFound;
	}

	private String checkLoginAccountByCpfAndPassword(Customer customer) {
		if (!checkCpf(customer.getCpf()))
			return "cpf invalido";
		if (customer.getPassword().isEmpty() || customer.getPassword().length() != 6)
			return "senha invalida";

		return "conta verificada";
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

	public Account findAccountByAgencyAndAccount(Account account) throws Exception {
		String checkedAccount = checkFindAccountByAgencyAndAccount(account);
		if (!checkedAccount.equals("conta verificada"))
			throw new Exception(checkedAccount);

		Account accountFound = accountRepository.findByAgencyAndAccount(account.getAgency(), account.getAccount())
				.orElse(null);
		if (accountFound == null)
			throw new Exception("conta não encontrada");

		return accountFound;
	}

	private String checkFindAccountByAgencyAndAccount(Account account) {
		if (account.getAgency().isEmpty() || account.getAgency().length() != 6)
			return "agência invalida";
		if (account.getAccount().isEmpty() || account.getAccount().length() != 7)
			return "conta invalida";

		return "conta verificada";
	}

	public String transferBalance(Long id1, Long id2, Account account) throws Exception {
		if (id1 == id2)
			throw new Exception("ids iguais");

		String balanceChecked = checkTransferBalance(account);
		if (!balanceChecked.equals("saldo verificado"))
			throw new Exception(balanceChecked);

		Account accountFound1 = accountRepository.findById(id1).orElse(null);
		if (accountFound1 == null)
			throw new Exception("conta1 não encontrada");

		Account accountFound2 = accountRepository.findById(id2).orElse(null);
		if (accountFound2 == null)
			throw new Exception("conta2 não encontrada");

		accountFound1.withdraw(account.getBalance());
		accountFound2.deposit(account.getBalance());
		accountRepository.save(accountFound1);
		accountRepository.save(accountFound2);
		return "saldo transferido";
	}

	private String checkTransferBalance(Account account) {
		if (account.getBalance().doubleValue() == 0)
			return "saldo nulo";

		return "saldo verificado";
	}
}

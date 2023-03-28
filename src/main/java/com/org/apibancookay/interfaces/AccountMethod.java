package com.org.apibancookay.interfaces;

import com.org.apibancookay.models.Account;

public interface AccountMethod {
	Account findByCpfAndPassword(String cpf, String password);
	Account findByAgencyAndAccount(String agency, String account);
	Account findById(Long id);
	Account save(Account account);
}

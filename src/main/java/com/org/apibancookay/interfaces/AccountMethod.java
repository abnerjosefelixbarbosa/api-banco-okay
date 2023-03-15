package com.org.apibancookay.interfaces;

import com.org.apibancookay.models.Account;

public interface AccountMethod {
	Account findByAccountId(Long accountId);
	Account findByAgencyAndAccount(String agency, String account);
	Account findById(Long id);
	Account save(Account account);
}

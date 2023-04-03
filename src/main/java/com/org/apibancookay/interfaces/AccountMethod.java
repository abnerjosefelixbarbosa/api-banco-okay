package com.org.apibancookay.interfaces;

import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;

public interface AccountMethod {
	Account loginAccountByCpfAndPassword(Customer customer) throws Exception;
	Account findAccountByAgencyAndAccount(Account account) throws Exception;
	String transferBalance(Long id1, Long id2, Account account) throws Exception;
}

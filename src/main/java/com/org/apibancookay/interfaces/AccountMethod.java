package com.org.apibancookay.interfaces;

import com.org.apibancookay.models.Account;

public interface AccountMethod {
	Account findByAccountId(Long accountId);
}

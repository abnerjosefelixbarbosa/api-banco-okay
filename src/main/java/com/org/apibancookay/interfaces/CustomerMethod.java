package com.org.apibancookay.interfaces;

import com.org.apibancookay.models.Customer;

public interface CustomerMethod {
	Customer findByCpfAndPassword(String cpf, String password);
}

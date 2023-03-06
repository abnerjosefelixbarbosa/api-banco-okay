package com.org.apibancookay.interfaces;

import java.util.Collection;

import com.org.apibancookay.models.Customer;

public interface CustomerMethod {
	Collection<Customer> getCustomers();
	Customer getCustomerById(Long id);
	Customer createCustomer(Customer customer);
	Customer updateCustomer(Long id, Customer customer);
}

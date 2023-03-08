package com.org.apibancookay.interfaces;

import java.util.Collection;

import com.org.apibancookay.models.Customer;

public interface CustomerMethod {
	Collection<Customer> getCustomers();
	Customer getCustomerById(Long id);
	String createCustomer(Customer customer);
	String updateCustomer(Long id, Customer customer);
	String deleteCustomerById(Long id);
}

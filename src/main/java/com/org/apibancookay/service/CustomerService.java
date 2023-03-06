package com.org.apibancookay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.models.Customer;
import com.org.apibancookay.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	public Customer createCustomer(Customer customer) {
		if (customerRepository.existsById(customer.getId())) {
			return null;
		}
		
		return customerRepository.save(customer);
	}
}

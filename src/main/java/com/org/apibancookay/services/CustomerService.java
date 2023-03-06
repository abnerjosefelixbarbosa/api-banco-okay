package com.org.apibancookay.services;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.CustomerMethod;
import com.org.apibancookay.models.Customer;
import com.org.apibancookay.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService implements CustomerMethod {
	@Autowired
	private CustomerRepository customerRepository;
	
	public Collection<Customer> getCustomers() {
		return customerRepository.findAll();
	}
	
	public Customer getCustomerById(Long id) {
		if (!customerRepository.existsById(id)) {
			return null;
		}
		
		return customerRepository.findById(id).get();
	}

	@Transactional
	public Customer createCustomer(Customer customer) {
		if (!customerRepository.existsById(customer.getId())) {
			return customerRepository.save(customer);
		}
		
		return null;
	}

	@Transactional
	public Customer updateCustomer(Long id, Customer customer) {
		if (!customerRepository.existsById(id)) {
			return null;
		}
		
		Customer customerFound = customerRepository.findById(id).get();
		BeanUtils.copyProperties(customer, customerFound);		
		return customerRepository.save(customerFound);
	}
}

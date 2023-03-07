package com.org.apibancookay.services;

import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.apibancookay.interfaces.CustomerMethod;
import com.org.apibancookay.models.Customer;
import com.org.apibancookay.repositories.CustomerRepository;

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
	public String createCustomer(Customer customer) {
		if (customerRepository.existsById(customer.getId())) {
			return "customer already exists";
		}
		if (customerRepository.existsByCpf(customer.getCpf())) {
			return "cpf already exists";
		}
		if (customerRepository.existsByRg(customer.getRg())) {
			return "rg already exists";
		}
		if (customerRepository.existsByEmail(customer.getEmail())) {
			return "email already exists";
		}
		if (customerRepository.existsByPassword(customer.getPassword())) {
			return "password already exists";
		}

		customerRepository.save(customer);
		return "customer created";
	}

	@Transactional
	public String updateCustomer(Long id, Customer customer) {
		if (!customerRepository.existsById(id)) {
			return "customer not found";
		} 
		
		Customer customerFound = customerRepository.findById(id).get();
		BeanUtils.copyProperties(customer, customerFound);
		customerRepository.save(customerFound);
		return "customer updated";
	}

	@Transactional
	public Customer deleteCustomerById(Long id) {
		if (!customerRepository.existsById(id)) {
			return null;
		}

		customerRepository.deleteById(id);
		return new Customer();
	}
}

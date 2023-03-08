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
			return "cliente já existe";
		}
		if (customerRepository.existsByCpf(customer.getCpf())) {
			return "cpf já existe";
		}
		if (customerRepository.existsByRg(customer.getRg())) {
			return "rg já existe";
		}
		if (customerRepository.existsByEmail(customer.getEmail())) {
			return "email já existe";
		}
		if (customerRepository.existsByPassword(customer.getPassword())) {
			return "senha já existe";
		}

		customerRepository.save(customer);
		return "cliente criado";
	}

	@Transactional
	public String updateCustomer(Long id, Customer customer) {
		if (!customerRepository.existsById(id)) {
			return "cliente não encontrado";
		} 
		if (id != customer.getId()) {
			return "id diferente";
		}
		if (customerRepository.existsByCpf(customer.getCpf())) {
			return "cpf já existe";
		}
		if (customerRepository.existsByRg(customer.getRg())) {
			return "rg já existe";
		}
		if (customerRepository.existsByEmail(customer.getEmail())) {
			return "email já existe";
		}
		if (customerRepository.existsByPassword(customer.getPassword())) {
			return "senha já existe";
		}
		
		Customer customerFound = customerRepository.findById(id).get();
		BeanUtils.copyProperties(customer, customerFound);
		customerRepository.save(customerFound);
		return "cliente alterado";
	}

	@Transactional
	public String deleteCustomerById(Long id) {
		if (!customerRepository.existsById(id)) {
			return "cliente não encontrado";
		}

		customerRepository.deleteById(id);
		return "cliente deletado";
	}
}

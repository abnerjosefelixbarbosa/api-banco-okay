package com.org.apibancookay.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.org.apibancookay.dtos.CustomerDTO;
import com.org.apibancookay.interfaces.CustomerMethod;
import com.org.apibancookay.models.Customer;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {
	@Autowired
	private CustomerMethod customerMethod;
	
	@GetMapping
	public ResponseEntity<?> getCustomers() {	
		Collection<Customer> customers = customerMethod.getCustomers();
		return ResponseEntity.status(200).body(customers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
		Customer customer = customerMethod.getCustomerById(id);
		if (customer == null) {
			return ResponseEntity.status(404).body(customer);
		}
		
		return ResponseEntity.status(200).body(customer);
	}

	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);

		String result = customerMethod.createCustomer(customer);
		if (!result.equals("cliente criado")) {
			return ResponseEntity.status(400).body(result);
		}

		return ResponseEntity.status(201).body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);

		String result = customerMethod.updateCustomer(id, customer);
		if (result.equals("cliente não encontrado")) {
			return ResponseEntity.status(404).body(result);
		}
		if (!result.equals("cliente alterado")) {
			return ResponseEntity.status(400).body(result);
		}

		return ResponseEntity.status(200).body(result);
	}
	
    @DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
		String result = customerMethod.deleteCustomerById(id);
		if (!result.equals("cliente deletado")) {
			return ResponseEntity.status(404).body(result);
		}
		
		return ResponseEntity.status(200).body(result);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}

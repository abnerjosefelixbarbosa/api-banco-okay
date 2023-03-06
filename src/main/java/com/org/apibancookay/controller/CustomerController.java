package com.org.apibancookay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.org.apibancookay.dto.CustomerDTO;
import com.org.apibancookay.models.Customer;
import com.org.apibancookay.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		
		try {
			customer = customerService.createCustomer(customer);
			if (customer == null) {
				return ResponseEntity.status(400).body(null);
			}
			
			BeanUtils.copyProperties(customer, customerDTO);
			return ResponseEntity.status(201).body(customerDTO);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(customerDTO);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}

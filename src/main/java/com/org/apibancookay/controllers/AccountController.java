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

import com.org.apibancookay.dtos.AccountDTO;
import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.models.Account;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
	@Autowired
	private AccountMethod accountMethod;
	
	@GetMapping
	public ResponseEntity<?> getAccounts() {	
		Collection<Account> customers = accountMethod.getAccounts();
		return ResponseEntity.status(200).body(customers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable Long id) {
		Account account = accountMethod.getAccountById(id);
		if (account == null) {
			return ResponseEntity.status(404).body(account);
		}
		
		return ResponseEntity.status(200).body(account);
	}
	
	@GetMapping("/{cpf}/{password}")
	public ResponseEntity<?> getAccountByCpfAndPassword(@PathVariable String cpf, @PathVariable String password) {
		Account account = accountMethod.getAccountByCpfAndPassword(cpf, password);
		if (account == null) {
			return ResponseEntity.status(404).body(account);
		}
		
		return ResponseEntity.status(200).body(account);
	}

	@PostMapping
	public ResponseEntity<?> createAccount(@RequestBody @Valid AccountDTO accountDTO) {
		Account account = new Account();
		BeanUtils.copyProperties(accountDTO, account);

		String result = accountMethod.createAccount(account);
		if (!result.equals("conta criada")) {
			return ResponseEntity.status(400).body(result);
		}

		return ResponseEntity.status(201).body(result);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDTO accountDTO) {
		Account account = new Account();
		BeanUtils.copyProperties(accountDTO, account);

		String result = accountMethod.updateAccount(id, account);
		if (!result.equals("conta alterada")) {
			return ResponseEntity.status(404).body(result);
		}

		return ResponseEntity.status(200).body(result);
	}
	
    @DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAccountById(@PathVariable Long id) {
    	String result = accountMethod.deleteAccountById(id);
		if (!result.equals("conta deletada")) {
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

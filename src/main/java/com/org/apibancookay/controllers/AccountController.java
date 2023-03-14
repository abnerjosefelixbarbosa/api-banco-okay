package com.org.apibancookay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.apibancookay.dtos.AccountDTO;
import com.org.apibancookay.dtos.CustomerDTO;
import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.interfaces.CustomerMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
	@Autowired
	private AccountMethod accountMethod;
	@Autowired
	private CustomerMethod customerMethod;
	
	@GetMapping("/login/{cpf}/{password}")
	public ResponseEntity<?> loginByCpfAndPassword(@PathVariable String cpf, @PathVariable String password) {
		try {
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setCpf(cpf);
			customerDTO.setPassword(password);
			
			String validLoginByCpfAndPassword = customerDTO.validLoginByCpfAndPassword();
			if (!validLoginByCpfAndPassword.isEmpty()) {
				return ResponseEntity.status(400).body(validLoginByCpfAndPassword);
			}
			
			Customer findByCpfAndPassword = customerMethod.findByCpfAndPassword(cpf, password);
			if (findByCpfAndPassword == null) {
				return ResponseEntity.status(404).body("conta não encontrado");
			}
			
			Account findById = accountMethod.findByAccountId(findByCpfAndPassword.getId());		
			return ResponseEntity.status(200).body(findById);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em login por cpf e password");
		}
	}
	
	@GetMapping("/{agency}/{account}")
	public ResponseEntity<?> findByAgencyAndAccount(@PathVariable String agency, @PathVariable String account) {
		try {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setAgency(agency);
			accountDTO.setAccount(account);
			
			String validFindByAgencyAndAccount = accountDTO.validFindByAgencyAndAccount();
			if (!validFindByAgencyAndAccount.isEmpty()) {
				return ResponseEntity.status(400).body(validFindByAgencyAndAccount);
			}
			
			Account findByAgencyAndAccount = accountMethod.findByAgencyAndAccount(agency, account);
			if (findByAgencyAndAccount == null) {
				return ResponseEntity.status(404).body("conta não encontrada");
			}
			
			return ResponseEntity.status(200).body(findByAgencyAndAccount);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em encontrar por agência e conta");
		}
	}
	
	@PutMapping("/transfer/{id1}/{id2}")
	public ResponseEntity<?> transferBalance(@PathVariable Long id1, @PathVariable Long id2, @RequestBody Account account) {
		try {
			if (id1 == id2) {
				return ResponseEntity.status(400).body("contas identicas");
			}
			
			return ResponseEntity.status(200).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em transferir saldo");
		}
	}
}

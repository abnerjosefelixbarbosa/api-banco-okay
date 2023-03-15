package com.org.apibancookay.controllers;

import org.springframework.beans.BeanUtils;
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
				return ResponseEntity.status(404).body("conta não encontrada");
			}
			
			Account findByAccountId = accountMethod.findByAccountId(findByCpfAndPassword.getId());	
			AccountDTO accountDTO = new AccountDTO();
			BeanUtils.copyProperties(findByAccountId, accountDTO);
			return ResponseEntity.status(200).body(accountDTO);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em login por cpf e senha");
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
			
			BeanUtils.copyProperties(findByAgencyAndAccount, accountDTO);
			return ResponseEntity.status(200).body(accountDTO);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em encontrar por agência e conta");
		}
	}
	
	@PutMapping("/transfer/{id1}/{id2}")
	public ResponseEntity<?> transferBalance(@PathVariable Long id1, @PathVariable Long id2, @RequestBody AccountDTO accountDTO) {
		try {
			if (id1 == id2) {
				return ResponseEntity.status(400).body("ids identicos");
			}
			String validTransferBalance = accountDTO.validTransferBalance();
			if (!validTransferBalance.isEmpty()) {
				return ResponseEntity.status(400).body(validTransferBalance);
			}
			
			Account account1 = accountMethod.findById(id1);
			if (account1 == null) {
				return ResponseEntity.status(404).body("conta1 não encontrada");
			}
			Account account2 = accountMethod.findById(id2);
			if (account2 == null) {
				return ResponseEntity.status(404).body("conta2 não encontrada");
			}
			
			account1.withdraw(accountDTO.getBalance());
			account2.deposit(accountDTO.getBalance());
			accountMethod.save(account1);
			accountMethod.save(account2);
			return ResponseEntity.status(200).body("saldo transferido");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em transferir saldo");
		}
	}
}

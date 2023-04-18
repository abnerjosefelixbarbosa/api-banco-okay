package com.org.apibancookay.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.apibancookay.dtos.AccountDTO;
import com.org.apibancookay.dtos.CustomerDTO;
import com.org.apibancookay.interfaces.AccountMethod;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	@Autowired
	private AccountMethod accountMethod;

	@Operation(description = "teste")
	@GetMapping("/teste")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(200).body("teste");
	}

	@Operation(description = "login by cpf and password")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "400", description = "Bad Request"),
		@ApiResponse(responseCode = "404", description = "Not Found") 
	})
	@PostMapping("/login-by-cpf-and-password")
	public ResponseEntity<?> loginByCpfAndPassword(@RequestBody CustomerDTO customerDTO) {
		try {
			Customer customer = new Customer();
			BeanUtils.copyProperties(customerDTO, customer);
			
			Account accountLogged = accountMethod.loginByCpfAndPassword(customer);
			if (accountLogged == null) {
				return ResponseEntity.status(404).body(accountLogged);
			} 
			
			return ResponseEntity.status(200).body(accountLogged);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@Operation(description = "encontrar conta pela agência e conta")
	@PostMapping("/find-account-by-agency-and-account")
	public ResponseEntity<?> findAccountByAgencyAndAccount(@RequestBody AccountDTO accountDTO) {
		try {
			Account account = new Account();
			BeanUtils.copyProperties(accountDTO, account);
			Account accountFound = accountMethod.findByAgencyAndAccount(account);
			BeanUtils.copyProperties(accountFound, accountDTO);
			return ResponseEntity.status(200).body(accountDTO);
		} catch (Exception e) {
			if (e.getMessage().equals("conta não encontrada"))
				return ResponseEntity.status(404).body(e.getMessage());
			if (!e.getMessage().equals("conta verificada"))
				return ResponseEntity.status(400).body(e.getMessage());			

			return ResponseEntity.status(500).body("erro no servidor");
		}
	}

	@Operation(description = "transfere saldo pelos dois ids da conta")
	@PutMapping("/transfer-balance/{id1}/{id2}")
	public ResponseEntity<?> transferBalance(@PathVariable Long id1, @PathVariable Long id2,
			@RequestBody AccountDTO accountDTO) {
		try {
			Account account = new Account();
			BeanUtils.copyProperties(accountDTO, account);
			return ResponseEntity.status(200).body(accountMethod.transferBalance(id1, id2, account));
		} catch (Exception e) {
			if (e.getMessage().equals("ids iguais"))
				return ResponseEntity.status(400).body(e.getMessage());
			if (e.getMessage().equals("conta1 não encontrada"))
				return ResponseEntity.status(404).body(e.getMessage());
			if (e.getMessage().equals("conta2 não encontrada"))
				return ResponseEntity.status(404).body(e.getMessage());
			if (!e.getMessage().equals("saldo verificado"))
				return ResponseEntity.status(400).body(e.getMessage());
			
			return ResponseEntity.status(500).body("erro no servidor");
		}
	}
}

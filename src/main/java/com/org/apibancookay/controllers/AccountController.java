package com.org.apibancookay.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
	@Autowired
	private AccountMethod accountMethod;

	@Operation(description = "teste")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna mensagem teste"), })
	@GetMapping("/teste")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(200).body("teste");
	}

	@Operation(description = "logar conta pelo cpf e senha")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna objeto conta"),
			@ApiResponse(responseCode = "400", description = "retorna mensagem cpf invalido"),
			@ApiResponse(responseCode = "400", description = "retorna mensagem senha invalida"),
			@ApiResponse(responseCode = "404", description = "retorna mensagem conta não encontrada"),
			@ApiResponse(responseCode = "500", description = "retorna mensagem erro no servidor"), })
	@PostMapping("/login-account-by-cpf-and-password")
	public ResponseEntity<?> loginAccountByCpfAndPassword(@RequestBody CustomerDTO customerDTO) {
		try {
			Customer customer = new Customer();
			BeanUtils.copyProperties(customerDTO, customer);
			Account accountLogged = accountMethod.loginAccountByCpfAndPassword(customer);
			AccountDTO accountDTO = new AccountDTO();
			BeanUtils.copyProperties(accountLogged, accountDTO);
			return ResponseEntity.status(200).body(accountDTO);
		} catch (Exception e) {
			if (!e.getMessage().equals("conta verificada"))
				return ResponseEntity.status(400).body(e.getMessage());
			if (e.getMessage().equals("conta não encontrada"))
				return ResponseEntity.status(404).body(e.getMessage());

			return ResponseEntity.status(500).body("erro no servidor");
		}
	}

	@Operation(description = "encontrar conta pela agência e conta")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna objeto conta"),
			@ApiResponse(responseCode = "400", description = "retorna mensagem agência invalida"),
			@ApiResponse(responseCode = "400", description = "retorna mensagem conta invalida"),
			@ApiResponse(responseCode = "404", description = "retorna mensagem conta não encontrada"),
			@ApiResponse(responseCode = "500", description = "retorna mensagem erro no servidor"), })
	@PostMapping("/find-account-by-agency-and-account")
	public ResponseEntity<?> findAccountByAgencyAndAccount(@RequestBody AccountDTO accountDTO) {
		try {
			Account account = new Account();
			BeanUtils.copyProperties(accountDTO, account);
			Account accountFound = accountMethod.findAccountByAgencyAndAccount(account);
			BeanUtils.copyProperties(accountFound, accountDTO);
			return ResponseEntity.status(200).body(accountDTO);
		} catch (Exception e) {
			if (!e.getMessage().equals("conta verificada"))
				return ResponseEntity.status(400).body(e.getMessage());
			if (e.getMessage().equals("conta não encontrada"))
				return ResponseEntity.status(404).body(e.getMessage());

			return ResponseEntity.status(500).body("erro no servidor");
		}
	}

	@Operation(description = "transfere saldo pelos dois ids da conta")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna mensagem saldo transferido"),
			@ApiResponse(responseCode = "400", description = "retorna mensagem ids iguais"),
			@ApiResponse(responseCode = "400", description = "retorna mensagem saldo nulo"),
			@ApiResponse(responseCode = "404", description = "retorna mensagem conta1 não encontrada"),
			@ApiResponse(responseCode = "404", description = "retorna mensagem conta2 não encontrada"),
			@ApiResponse(responseCode = "500", description = "retorna menssagem erro no servidor"), })
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

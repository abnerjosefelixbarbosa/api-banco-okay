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
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna teste"), })
	@GetMapping("/teste")
	public ResponseEntity<?> test() {
		return ResponseEntity.status(200).body("teste");
	}

	@Operation(description = "loga conta pelo cpf e senha")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna conta"),
			@ApiResponse(responseCode = "400", description = "retorna messagem dados invalidos"),
			@ApiResponse(responseCode = "404", description = "retorna conta não encontrada"),
			@ApiResponse(responseCode = "500", description = "retorna erro em login por cpf e senha"), })
	@PostMapping("/login_cpf_password")
	public ResponseEntity<?> loginByCpfAndPassword(@RequestBody CustomerDTO customerDTO) {
		try {
			String validLoginByCpfAndPassword = customerDTO.validLoginByCpfAndPassword();
			if (!validLoginByCpfAndPassword.isEmpty()) {
				return ResponseEntity.status(400).body(validLoginByCpfAndPassword);
			}

			String cpf = customerDTO.getCpf();
			String password = customerDTO.getPassword();
			Account findByCpfAndPassword = accountMethod.findByCpfAndPassword(cpf, password);
			if (findByCpfAndPassword == null) {
				return ResponseEntity.status(404).body("conta não encontrada");
			}

			AccountDTO accountDTO = new AccountDTO();
			BeanUtils.copyProperties(findByCpfAndPassword, accountDTO);
			return ResponseEntity.status(200).body(accountDTO);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("erro em login por cpf e senha");
		}
	}

	@Operation(description = "encontra conta pela agência e conta")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna conta"),
			@ApiResponse(responseCode = "400", description = "retorna messagem dados invalidos"),
			@ApiResponse(responseCode = "404", description = "retorna conta não encontrada"),
			@ApiResponse(responseCode = "500", description = "retorna erro em encotrar por cpf e senha"), })
	@PostMapping("/find_agencia_conta")
	public ResponseEntity<?> findByAgencyAndAccount(@RequestBody AccountDTO accountDTO) {
		try {
			String validFindByAgencyAndAccount = accountDTO.validFindByAgencyAndAccount();
			if (!validFindByAgencyAndAccount.isEmpty()) {
				return ResponseEntity.status(400).body(validFindByAgencyAndAccount);
			}

			String agency = accountDTO.getAgency();
			String account = accountDTO.getAccount();
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

	@Operation(description = "transfere saldo pelos dois ids da conta")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "retorna saldo transferido"),
			@ApiResponse(responseCode = "400", description = "retorna messagem dados invalidos"),
			@ApiResponse(responseCode = "404", description = "retorna conta1 ou conta2 não encontrada"),
			@ApiResponse(responseCode = "500", description = "retorna erro em transferir saldo"), })
	@PutMapping("/transfer_balance/{id1}/{id2}")
	public ResponseEntity<?> transferBalance(@PathVariable Long id1, @PathVariable Long id2,
			@RequestBody AccountDTO accountDTO) {
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

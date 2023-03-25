package com.org.apibancookay.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.caelum.stella.validation.CPFValidator;
import jakarta.mail.internet.InternetAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"name", "cpf", "rg", "email", "password", "birthDate"})
public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cpf;
	private String rg;
	private String email;
	private String password;
	private LocalDate birthDate;
	
	public String validLoginByCpfAndPassword() {
		if (!validCpf()) {
			return "cpf invalido";
		}
		if (password.isEmpty()) {
			return "senha obrigatória";
		}
		if (password.length() != 6) {
			return "tamanho da senha diferente de 6";
		}
		
		return "";
	}
	
	public boolean validCpf() {
		CPFValidator cpfValidator = new CPFValidator(); 
		String cpf = this.cpf.replace(".", "").replace("-", "");
		
		try {
			cpfValidator.assertValid(cpf);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validEmail() {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

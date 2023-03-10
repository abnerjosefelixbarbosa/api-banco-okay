package com.org.apibancookay.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.caelum.stella.validation.CPFValidator;
import jakarta.mail.internet.InternetAddress;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "id nulo")
	private Long id;
	@Length(max = 100, message = "nome até 100 caracteres")
	@NotEmpty(message = "nome vazio")
	private String name;
	@Length(max = 20, message = "cpf até 20 caracteres")
	@CPF(message = "cpf invalido")
	private String cpf;
	@Length(max = 20, message = "rg até 20 caracteres")
	@NotEmpty(message = "rg vazio")
	private String rg;
	@Length(max = 50, message = "email até 50 caracteres")
	@Email(message = "email invalido")
	private String email;
	@Size(min = 6, max = 6, message = "senha até 6 caracteres")
	@NotEmpty(message = "senha vazia")
	private String password;
	@NotNull(message = "data nula")
	private LocalDate birthDate;
	
	public String validationOfCreateCustomer() {
		if (id == null) {
			return "id nulo";
		}
		if (name.isEmpty()) {
			return "nome vazio";
		}
		if (name.length() > 100) {
			return "nome maior que 100 caracteres";
		}
		if (!validCpf()) {
			return "cpf invalido";
		}
		if (rg.isEmpty() || rg.length() > 20) {
			return "rg invalido";
		}
		if (!validEmail() || email.length() > 50) {
			return "email invalido";
		}
		if (password.isEmpty() || password.length() != 6) {
			return "senha invalida";
		}
		if (birthDate == null) {
			return "data nula";
		}
		
		return "";
	}
	
	public String validationOfUpdateCustomer() {
		if (id == null) {
			return "id nulo";
		}
		if (name.isEmpty()) {
			return "nome vazio";
		}
		if (name.length() > 100) {
			return "nome maior que 100 caracteres";
		}
		if (!validCpf()) {
			return "cpf invalido";
		}
		if (rg.isEmpty() || rg.length() > 20) {
			return "rg invalido";
		}
		if (!validEmail() || email.length() > 50) {
			return "email invalido";
		}
		if (password.isEmpty() || password.length() != 6) {
			return "senha invalida";
		}
		if (birthDate == null) {
			return "data nula";
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", name=" + name + ", cpf=" + cpf + ", rg=" + rg + ", email=" + email
				+ ", password=" + password + ", birthDate=" + birthDate + "]";
	}
}

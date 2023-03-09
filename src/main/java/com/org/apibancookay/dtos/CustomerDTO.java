package com.org.apibancookay.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CustomerDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "id nulo")
	private Long id;
	@Length(min = 100, max = 100, message = "nome até 100 caracteres")
	@NotEmpty(message = "nome vazio")
	private String name;
	@Length(min = 20, max = 20, message = "cpf até 20 caracteres")
	@CPF(message = "cpf invalido")
	private String cpf;
	@Length(min = 20, max = 20, message = "rg até 20 caracteres")
	@NotEmpty(message = "rg vazio")
	private String rg;
	@Length(min = 50, max = 50, message = "email até 50 caracteres")
	@Email(message = "email invalido")
	private String email;
	@Length(min = 6, max = 6, message = "senha até 6 caracteres")
	@NotEmpty(message = "senha vazia")
	private String password;
	@NotNull(message = "data nula")
	private LocalDate birthDate;

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

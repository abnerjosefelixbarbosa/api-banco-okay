package com.org.apibancookay.models;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column(length = 100, nullable = false)
	private String name;
	@Column(length = 20, nullable = false, unique = true)
	private String cpf;
	@Column(length = 20, nullable = false, unique = true)
	private String rg;
	@Column(length = 50, nullable = false, unique = true)
	private String email;
	@Column(length = 6, nullable = false, unique = true)
	private String password;
	@Column(nullable = false)
	private LocalDate birthDate;
	@JsonIgnore
	@OneToOne(mappedBy = "customer")
	private Account account;
	@JsonIgnore
	@OneToOne(mappedBy = "customer")
	private Address address;
	@JsonIgnore
	@OneToOne(mappedBy = "customer")
	private Telephone telephone;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Telephone getTelephone() {
		return telephone;
	}

	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", cpf=" + cpf + ", rg=" + rg + ", email=" + email
				+ ", password=" + password + ", birthDate=" + birthDate + "]";
	}
}

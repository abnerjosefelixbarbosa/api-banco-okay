package com.org.apibancookay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import com.org.apibancookay.models.Customer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	@NotEmpty
	private String agency;
	@NotEmpty
	private String account;
	@DecimalMin(value = "0.01")
	private BigDecimal balance;
	@NotEmpty
	private String password;
	@NotNull
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", agency=" + agency + ", account=" + account + ", balance=" + balance
				+ ", password=" + password + "]";
	}
}

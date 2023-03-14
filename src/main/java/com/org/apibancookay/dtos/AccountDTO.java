package com.org.apibancookay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import com.org.apibancookay.models.Customer;

public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String agency;
	private String account;
	private BigDecimal balance;
	private String password;
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

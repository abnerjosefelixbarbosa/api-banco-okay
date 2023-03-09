package com.org.apibancookay.models;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column(length = 20, nullable = false, unique = true)
	private String agency;
	@Column(length = 20, nullable = false, unique = true)
	private String account;
	@Column(scale = 2, nullable = false)
	private BigDecimal balance;
	@Column(length = 4, nullable = false, unique = true)
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, unique = true)
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
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
		return "Account [id=" + id + ", agency=" + agency + ", account=" + account + ", balance=" + balance
				+ ", password=" + password + "]";
	}
}

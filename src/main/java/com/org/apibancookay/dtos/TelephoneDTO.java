package com.org.apibancookay.dtos;

import java.io.Serializable;
import java.util.Objects;

import com.org.apibancookay.models.Customer;

public class TelephoneDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String telephone;
	private Customer customer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TelephoneDTO other = (TelephoneDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "TelephoneDTO [id=" + id + ", telephone=" + telephone + "]";
	}
}
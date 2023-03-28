package com.org.apibancookay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apibancookay.models.Account;
import com.org.apibancookay.models.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Disabled
	public void loginByCpfAndPassword() throws Exception {
		Customer customer = new Customer();
		customer.setCpf("949.612.154-30");
		customer.setPassword("481228");

		String json = objectMapper.writeValueAsString(customer);
		String url = String.format("/accounts/login_cpf_password");
		MvcResult mvcResult = mockMvc.perform(post(url).contentType("application/json").content(json)).andReturn();

		json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		Account account = objectMapper.readValue(json, Account.class);
		System.out.println(account.toString());

		Assertions.assertEquals(200, status);
	}

	@Test
	@Disabled
	public void findByAgencyAndAccount() throws Exception {
		Account account = new Account();
		account.setAgency("1568-1");
		account.setAccount("13681-1");

		String json = objectMapper.writeValueAsString(account);
		String url = String.format("/accounts/find_agencia_conta");
		MvcResult mvcResult = mockMvc.perform(post(url).contentType("application/json").content(json)).andReturn();

		json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		account = objectMapper.readValue(json, Account.class);
		System.out.println(account.toString());

		Assertions.assertEquals(200, status);
	}

	@Test
	@Disabled
	public void transferBalance() throws Exception {
		Account account = new Account();
		account.setBalance(new BigDecimal("0.50"));

		String json = objectMapper.writeValueAsString(account);
		String url = String.format("/accounts/transfer_balance/%s/%s", "1", "2");
		MvcResult mvcResult = mockMvc.perform(put(url).contentType("application/json").content(json)).andReturn();

		json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);

		Assertions.assertEquals(200, status);
	}
}

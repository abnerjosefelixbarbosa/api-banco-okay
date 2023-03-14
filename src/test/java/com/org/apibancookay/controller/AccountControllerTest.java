package com.org.apibancookay.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
		String url = String.format("/accounts/login/%s/%s", "949.612.154-30", "481228");
		MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		Account account = objectMapper.readValue(json, Account.class);
		System.out.println(account.toString());
		
		Assertions.assertEquals(200, status);
	}
	
	@Test
	public void findByAgencyAndAccount() throws Exception {
		String url = String.format("/accounts/%s/%s", "1568-1", "13681-1");
		MvcResult mvcResult = mockMvc.perform(get(url)).andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		Account account = objectMapper.readValue(json, Account.class);
		System.out.println(account.toString());
		
		Assertions.assertEquals(200, status);
	}
}

package com.org.apibancookay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
	@DisplayName("obter contas")
	public void getAccounts() throws Exception {
		String url = String.format("/accounts");
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}
	
	@Test
	@Disabled
	@DisplayName("obter conta por id")
	public void getAccountById() throws Exception {
		String url = String.format("/accounts/%d", 1L);
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}
	
	@Test
	@Disabled
	@DisplayName("obter conta por cpf e senha")
	public void getAccountByCpfAndPassword() throws Exception {
		String url = String.format("/accounts/%s/%s", "949.612.154-30", "481228");
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}

	@Test
	@Disabled
	@DisplayName("criar conta")
	public void createAccount() throws Exception {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		Account account1 = new Account();
		account1.setId(1L);
		account1.setAgency("1568-1");
		account1.setAccount("13681-1");
		account1.setBalance(new BigDecimal("1000.00"));
		account1.setPassword("4812");
		account1.setCustomer(customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(2L);
		Account account2 = new Account();
		account2.setId(2L);
		account2.setAgency("2210-1");
		account2.setAccount("21224-1");
		account2.setBalance(new BigDecimal("400.00"));
		account2.setPassword("5832");
		account2.setCustomer(customer2);
		
		String json = objectMapper.writeValueAsString(account2);		
		String url = String.format("/accounts");
        MvcResult mvcResult = mockMvc.perform(post(url)
			   .contentType("application/json")
			   .content(json))
		       .andReturn();
		
        json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
        System.out.println(json);
		Assertions.assertEquals(201, status);
	}
	
	@Test
	//@Disabled
	@DisplayName("atualizar conta")
	public void updateAccount() throws Exception {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		Account account1 = new Account();
		account1.setId(1L);
		account1.setAgency("1568-1");
		account1.setAccount("13681-1");
		account1.setBalance(new BigDecimal("1000.00"));
		account1.setPassword("4812");
		account1.setCustomer(customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(2L);
		Account account2 = new Account();
		account2.setId(2L);
		account2.setAgency("2210-1");
		account2.setAccount("21224-1");
		account2.setBalance(new BigDecimal("400.00"));
		account2.setPassword("5832");
		account2.setCustomer(customer2);
		
		String json = objectMapper.writeValueAsString(account1);		
		String url = String.format("/accounts/%d", 1L);		
		MvcResult mvcResult = mockMvc.perform(put(url)
				   .contentType("application/json")
				   .content(json))
			       .andReturn();
			
		json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}
	
	@Test
	@Disabled
	@DisplayName("deletar aconta pelo id")
	public void deleteAccountById() throws Exception {
		String url = String.format("/account/%d", 1L);		
		MvcResult mvcResult = mockMvc.perform(delete(url))
			       .andReturn();
			
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}
}

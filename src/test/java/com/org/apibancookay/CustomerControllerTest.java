package com.org.apibancookay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;

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
import com.org.apibancookay.models.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Disabled
	@DisplayName("obter todos os clientes")
	public void getCustomers() throws Exception {
		String url = String.format("/customers");
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
	    System.out.println(json);
		Assertions.assertEquals(200, status);
	}
	
	@Test
	@Disabled
	@DisplayName("obter cliente pelo id")
	public void getCustomerById() throws Exception {
		String url = String.format("/customers/%d", 1L);
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}

	@Test
	@Disabled
	@DisplayName("criar cliente")
	public void createCustomer() throws Exception {
		LocalDate date1 = LocalDate.parse("1991-01-16");
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setName("Alberto Camara");
		customer1.setCpf("949.612.154-30");
		customer1.setRg("36.825.176");
		customer1.setEmail("cliente1@gmail.com");
		customer1.setPassword("481228");		
		customer1.setBirthDate(date1);
		
		LocalDate date2 = LocalDate.parse("2005-01-17");
		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setName("Barbara Cardoso");
		customer2.setCpf("370.897.974-57");
		customer2.setRg("29.254.761");
		customer2.setEmail("cliente2@gmail.com");
		customer2.setPassword("583245");		
		customer2.setBirthDate(date2);
		
		String json = objectMapper.writeValueAsString(customer1);		
		String url = String.format("/customers");
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
	@Disabled
	@DisplayName("atualizar cliente")
	public void updateCustomer() throws Exception {
		LocalDate date1 = LocalDate.parse("1991-01-16");
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setName("Alberto Camara");
		customer1.setCpf("949.612.154-30");
		customer1.setRg("36.825.176");
		customer1.setEmail("cliente1@gmail.com");
		customer1.setPassword("481228");		
		customer1.setBirthDate(date1);
		
		//customer1.setCpf("047.183.990-68");
		//customer1.setRg("19.713.427");
		//customer1.setEmail("cliente3@gmail.com");
		//customer1.setPassword("472225");
		
		LocalDate date2 = LocalDate.parse("2005-01-17");
		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setName("Barbara Cardoso");
		customer2.setCpf("370.897.974-57");
		customer2.setRg("29.254.761");
		customer2.setEmail("cliente2@gmail.com");
		customer2.setPassword("583245");		
		customer2.setBirthDate(date2);
		
		String json = objectMapper.writeValueAsString(customer1);		
		String url = String.format("/customers/%d", 1L);		
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
	@DisplayName("deletar cliente pelo id")
	public void deleteCustomerById() throws Exception {
		String url = String.format("/customers/%d", 1L);		
		MvcResult mvcResult = mockMvc.perform(delete(url))
			       .andReturn();
		
		String json = mvcResult.getResponse().getContentAsString();
		int status = mvcResult.getResponse().getStatus();
		System.out.println(json);
		Assertions.assertEquals(200, status);
	}
}

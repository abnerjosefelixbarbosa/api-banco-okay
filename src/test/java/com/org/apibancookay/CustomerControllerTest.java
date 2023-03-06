package com.org.apibancookay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
	public void getCustomers() throws Exception {
		String url = String.format("/customers");
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
	@Test
	@Disabled
	public void getCustomersById() throws Exception {
		String url = String.format("/customers/%d", 1L);
		MvcResult mvcResult = mockMvc.perform(get(url))
				.andReturn();
		
		Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	@Disabled
	public void createCustomer() throws Exception {
		//SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
		//Date date = formato.parse("1991-01-16");
		LocalDate date = LocalDate.parse("1991-01-16");
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Alberto Camara");
		customer.setCpf("94961215430");
		customer.setRg("36.825.176");
		customer.setEmail("clientea@gmail.com");
		customer.setPassword("481228");		
		customer.setBirthDate(date);
		String json = objectMapper.writeValueAsString(customer);
		String url = String.format("/customers");

		MvcResult mvcResult = mockMvc.perform(post(url)
			   .contentType("application/json")
			   .content(json))
		       .andReturn();
		
		Assertions.assertEquals(201, mvcResult.getResponse().getStatus());
	}
	
	@Test
	@Disabled
	public void updateCustomer() throws Exception {
		//SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
		//Date date = formato.parse("1991-01-16");
		LocalDate date = LocalDate.parse("1991-01-16");
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Alberto Camar");
		customer.setCpf("94961215430");
		customer.setRg("36.825.176");
		customer.setEmail("clientea@gmail.com");
		customer.setPassword("481228");		
		customer.setBirthDate(date);
		String json = objectMapper.writeValueAsString(customer);
		String url = String.format("/customers/%d", 1L);		
		
		MvcResult mvcResult = mockMvc.perform(put(url)
				   .contentType("application/json")
				   .content(json))
			       .andReturn();
			
		Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
	}
}

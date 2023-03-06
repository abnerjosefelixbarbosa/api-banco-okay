package com.org.apibancookay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
	public void createCustomer() throws Exception {
		// 949.612.154-30/481228
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = formato.parse("1991-01-16");
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Alberto Camara");
		customer.setCpf("94961215430");
		customer.setRg("36.825.176");
		customer.setEmail("clientea@gmail.com");
		customer.setPassword("481228");		
		customer.setBirthDate(date);
		String json = objectMapper.writeValueAsString(customer);

		mockMvc.perform(post("/customers")
				.contentType("application/json")
				.content(json))
		        .andExpect(status().is(201));
	}
}

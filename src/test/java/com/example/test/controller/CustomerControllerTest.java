package com.example.test.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.test.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CustomerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	private static ObjectMapper objectMapper = new ObjectMapper();

	final Customer customer1 = Customer.builder().email("controllertest@naver.com").nickname("controllertest")
			.password("controller").status(false).build();

	@Before
	public void setup() throws Exception {
		String param = objectMapper.writeValueAsString(customer1);
		this.mockMvc.perform(post("/api/customer").contentType(MediaType.APPLICATION_JSON).content(param))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testGetCustomers() throws Exception { // when // then
		this.mockMvc.perform(get("/api/customer").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4))).andDo(print());
	}
}

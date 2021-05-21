package com.example.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.test.model.Customer;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CustomerRepositoryTest {
	@Autowired
	CustomerRepository repository;
	
	@Test
	public void save() {
		Customer customer = new Customer();
		customer.setNickname("nakhoon");
		customer.setEmail("cnh6123@naver.com");
		
		repository.save(customer);
		
		Customer result = repository.findById(customer.get_id()).get();
		assertEquals(customer.getNickname(), result.getNickname());
	}
	
	@Test
	public void select() {
		Customer result = repository.findByEmail("test@test.com").get(0);
		assertEquals("test", result.getNickname());
	}
	
	@Test
	public void change() {
		Customer customer = repository.findByEmail("test@test.com").get(0);
		String expect = new String("");
		if(customer.getNickname().equals("test")) {
			expect = new String("test2");
			customer.setNickname("test2");
		}else {
			expect = new String("test");
			customer.setNickname("test");
		}

		assertEquals(expect, customer.getNickname());
	}
	
	@Test
	public void delete() {
		Customer customer = repository.findByEmail("cnh6123@naver.com").get(0);
		
		repository.delete(customer);
		
		assertEquals(0, repository.findByEmail("cnh6123@naver.com").size());
	}
}

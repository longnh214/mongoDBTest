package com.example.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Customer;
import com.example.test.repository.CustomerRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/customer")
	@ApiOperation(value = "getAllCustomers", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<>(customerRepository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping("/customer")
	@ApiOperation(value = "addCustomer")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "customer", value = "회원 객체", required = true, dataType = "customer")
	})
	public ResponseEntity<Boolean> addCustomer(@RequestBody Customer customer) {
		try {
			if(customerRepository.save(customer) != null) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/customer")
	@ApiOperation(value = "updateCustomer")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "customer", value = "회원 객체", required = true, dataType = "customer")
	})
	public ResponseEntity<Boolean> updateCustomer(@RequestBody Customer customer) {
		try {
			List<Customer> findcustomerList = customerRepository.findByEmail(customer.getEmail());
			if(findcustomerList.size() == 1) {
				Customer precustomer = findcustomerList.get(0);
				precustomer.setPassword(customer.getPassword());
				precustomer.setNickname(customer.getNickname());
				customerRepository.save(precustomer);
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/customer")
	@ApiOperation(value = "deleteCustomer")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "customer", value = "회원 객체", required = true, dataType = "customer")
	})
	public ResponseEntity<Boolean> deleteCustomer(@RequestBody Customer customer) {
		try {
			
			if(customer != null) {
				customerRepository.delete(customer);
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

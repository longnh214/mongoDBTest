package com.example.test.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.test.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	List<Customer> findByEmail(String email);
}
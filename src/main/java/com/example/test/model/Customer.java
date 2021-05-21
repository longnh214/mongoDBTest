package com.example.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "customers")
@Builder
public class Customer {
	@Id
	private String _id;
	
	private String email;
	private String password;
	private String nickname;
	private boolean status;
	
	public Customer(String email, String password, String nickname){
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}
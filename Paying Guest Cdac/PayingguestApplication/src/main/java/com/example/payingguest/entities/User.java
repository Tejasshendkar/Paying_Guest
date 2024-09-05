package com.example.payingguest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String name;
	private String email;
	private String mobileNo;
	private String address;
	private String password;
	private String role;
	
	
	
	public User() {

	}
	

	public User(Long userId, String name, String email, String mobileNo, String address, String password,
			String role) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.address = address;
		this.password = password;
		this.role = role;
	}
	
	
	public User( String name, String email, String mobileNo, String address, String password,String role) {
		this.name = name;
		this.email = email;
		this.mobileNo = mobileNo;
		this.address = address;
		this.password = password;
		this.role = role;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

	
	

	

	
}

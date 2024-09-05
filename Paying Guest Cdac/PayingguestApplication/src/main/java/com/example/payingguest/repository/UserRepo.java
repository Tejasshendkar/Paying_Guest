package com.example.payingguest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {
	List<User>  findByRole(String role);

	User findByEmail(String email);
}

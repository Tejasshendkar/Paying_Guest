package com.example.payingguest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.PG;

public interface PgRepo extends JpaRepository<PG, Long>{

	List<PG> findByAddress(String address);
	List<PG> findByUserRefUserId(Long userId);
	       
}

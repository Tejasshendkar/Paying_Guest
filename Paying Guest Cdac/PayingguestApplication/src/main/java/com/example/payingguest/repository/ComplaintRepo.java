package com.example.payingguest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.Complaint;

public interface ComplaintRepo extends JpaRepository<Complaint, Long> {

	//List<Complaint> findByUserRef(Long id);
}

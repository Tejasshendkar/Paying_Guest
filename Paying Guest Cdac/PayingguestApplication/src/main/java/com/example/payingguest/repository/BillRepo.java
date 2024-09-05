package com.example.payingguest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.Bill;

public interface BillRepo extends JpaRepository<Bill, Long> {
            List<Bill> findByUserRefUserId(Long userId);
}

package com.example.payingguest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}

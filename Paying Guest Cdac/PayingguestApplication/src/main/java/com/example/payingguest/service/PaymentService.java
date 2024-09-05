package com.example.payingguest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payingguest.repository.PaymentRepo;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepo paymentRepoRef;
	
	
	
	
}

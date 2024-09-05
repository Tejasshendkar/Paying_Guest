package com.example.payingguest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	private double amount;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User userRef;

	@ManyToOne
	@JoinColumn(name = "billId", nullable = false)
	private Bill billRef;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	public enum PaymentStatus {
		PENDING, COMPLETED, FAILED
	}

	public Payment() {

	}

	public Payment(Long paymentId, double amount, User userRef, Bill billRef, PaymentStatus paymentStatus) {
		this.paymentId = paymentId;
		this.amount = amount;
		this.userRef = userRef;
		this.billRef = billRef;
		this.paymentStatus = paymentStatus;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public User getUserRef() {
		return userRef;
	}

	public void setUserRef(User userRef) {
		this.userRef = userRef;
	}

	public Bill getBillRef() {
		return billRef;
	}

	public void setBillRef(Bill billRef) {
		this.billRef = billRef;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", amount=" + amount + ", userRef=" + userRef + ", billRef="
				+ billRef + ", paymentStatus=" + paymentStatus + "]";
	}

}

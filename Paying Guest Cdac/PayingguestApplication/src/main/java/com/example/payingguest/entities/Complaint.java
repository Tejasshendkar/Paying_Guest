package com.example.payingguest.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Complaint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long complaintId;
	private String subject;
	private String massage;
	private LocalDate complaintDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private User userRef;
	
	

	public Complaint() {

	}

	public Complaint(Long complaintId, String subject, String massage, LocalDate complaintDate, User userRef) {
		super();
		this.complaintId = complaintId;
		this.subject = subject;
		this.massage = massage;
		this.complaintDate = complaintDate;
		this.userRef = userRef;
	}

	public Long getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}

	public User getUserRef() {
		return userRef;
	}

	public void setUserRef(User userRef) {
		this.userRef = userRef;
	}

	@Override
	public String toString() {
		return "Complaint [complaintId=" + complaintId + ", subject=" + subject + ", massage=" + massage
				+ ", complaintDate=" + complaintDate + ", userRef=" + userRef + "]";
	}

}

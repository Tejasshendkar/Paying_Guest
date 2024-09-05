package com.example.payingguest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PG {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pgId;
	private String pgName;
	private String contactNo;
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	//@Cascade(CascadeType.ALL)
	private User userRef;

	public PG() {

	}

	

	public PG(Long pgId, String pgName, String ownerName, String contactNo, String address, User userRef) {
		super();
		this.pgId = pgId;
		this.pgName = pgName;
		this.contactNo = contactNo;
		this.address = address;
		this.userRef = userRef;
	}



	public Long getPgId() {
		return pgId;
	}

	public void setPgId(Long pgId) {
		this.pgId = pgId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = pgName;
	}


	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}



	public User getUserRef() {
		return userRef;
	}



	public void setUserRef(User userRef) {
		this.userRef = userRef;
	}



	@Override
	public String toString() {
		return "PG [pgId=" + pgId + ", pgName=" + pgName + ", contactNo=" + contactNo + ", address=" + address
				+ ", userRef=" + userRef + "]";
	}
	

}

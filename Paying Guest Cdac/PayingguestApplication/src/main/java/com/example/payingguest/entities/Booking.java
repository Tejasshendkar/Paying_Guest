package com.example.payingguest.entities;

import java.time.LocalDate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	private LocalDate bookingDate;
	private LocalDate checkOutDate;

	public enum BookingStatus {
		CONFIRMED , PENDING, CANCELLED,CHECKEDOUT
	}

	@Enumerated(EnumType.STRING)
	private BookingStatus bookingStatus;

	@ManyToOne
	@JoinColumn(name = "userId")
	@Cascade(CascadeType.ALL)
	private User userRef;

	@ManyToOne
	@JoinColumn(name = "roomId")
	@Cascade(CascadeType.ALL)
	private Room roomRef;

	public Booking() {

	}

	public Booking(Long bookingId, LocalDate bookingDate, User userRef) {
		this.bookingId = bookingId;
		this.bookingDate = bookingDate;
		this.userRef = userRef;
	}

	

	public Booking(Long bookingId, LocalDate bookingDate, LocalDate checkOutDate, BookingStatus bookingStatus,
			User userRef, Room roomRef) {
		this.bookingId = bookingId;
		this.bookingDate = bookingDate;
		this.checkOutDate = checkOutDate;
		this.bookingStatus = bookingStatus;
		this.userRef = userRef;
		this.roomRef = roomRef;
	}

	public Room getRoomRef() {
		return roomRef;
	}

	public void setRoomRef(Room roomRef) {
		this.roomRef = roomRef;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public User getUserRef() {
		return userRef;
	}

	public void setUserRef(User userRef) {
		this.userRef = userRef;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookingDate=" + bookingDate + ", checkOutDate=" + checkOutDate
				+ ", bookingStatus=" + bookingStatus + ", userRef=" + userRef + ", roomRef=" + roomRef + "]";
	}

	

}

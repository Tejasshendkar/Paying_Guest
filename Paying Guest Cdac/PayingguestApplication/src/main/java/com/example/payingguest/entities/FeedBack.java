package com.example.payingguest.entities;

import java.time.LocalDate;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FeedBack {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feedBackId;
	private String comment;
	private LocalDate feedBackDate;
	private int rating;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	@Cascade(CascadeType.ALL)
	User userRef;
	
	@ManyToOne
	@JoinColumn(name = "pgId")
	PG pgRef;
	
	
	public FeedBack() {
		
	}


	public FeedBack(Long feedBackId, String comment, LocalDate feedBackLocalDate, int rating, User userRef, PG pgRef) {
		this.feedBackId = feedBackId;
		this.comment = comment;
		this.feedBackDate = feedBackLocalDate;
		this.rating = rating;
		this.userRef = userRef;
		this.pgRef = pgRef;
	}


	public FeedBack(String comment, LocalDate feedBackLocalDate, int rating) {
		this.comment = comment;
		this.feedBackDate = feedBackLocalDate;
		this.rating = rating;
	}


	public Long getFeedBackId() {
		return feedBackId;
	}


	public void setFeedBackId(Long feedBackId) {
		this.feedBackId = feedBackId;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public LocalDate getFeedBackDate() {
		return feedBackDate;
	}


	public void setFeedBackLocalDate(LocalDate feedBackDate) {
		this.feedBackDate = feedBackDate;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public User getUserRef() {
		return userRef;
	}


	public void setUserRef(User userRef) {
		this.userRef = userRef;
	}


	public PG getPgRef() {
		return pgRef;
	}


	public void setPgRef(PG pgRef) {
		this.pgRef = pgRef;
	}


	@Override
	public String toString() {
		return "FeedBack [feedBackId=" + feedBackId + ", comment=" + comment + ", feedBackDate=" + feedBackDate
				+ ", rating=" + rating + ", userRef=" + userRef + ", pgRef=" + pgRef + "]";
	}


	
	
	
	
	
	
	
}

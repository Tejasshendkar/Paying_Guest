package com.example.payingguest.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Images {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imagedata;

	@ManyToOne
	@JoinColumn(name = "roomId")
	@Cascade(CascadeType.ALL)
	private Room roomRef;

	@ManyToOne
	@JoinColumn(name = "pgId")
	@Cascade(CascadeType.ALL)
	private PG pgRef;

	public Images() {

	}

	public Images(Long id, byte[] imagedata, Room roomRef, PG pgRef) {
		super();
		this.id = id;
		this.imagedata = imagedata;
		this.roomRef = roomRef;
		this.pgRef = pgRef;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImagedata() {
		return imagedata;
	}

	public void setImagedata(byte[] imagedata) {
		this.imagedata = imagedata;
	}

	public Room getRoomRef() {
		return roomRef;
	}

	public void setRoomRef(Room roomRef) {
		this.roomRef = roomRef;
	}

	public PG getPgRef() {
		return pgRef;
	}

	public void setPgRef(PG pgRef) {
		this.pgRef = pgRef;
	}

}

package com.example.payingguest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payingguest.entities.Booking;
import com.example.payingguest.entities.Booking.BookingStatus;
import com.example.payingguest.entities.Room;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.repository.BookingRepo;
import com.example.payingguest.repository.RoomRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepo bookingRepoRef;

	@Autowired
	private RoomService roomServiceRef;

	@Autowired
	private UserService userServiceRef;

	@Autowired
	RoomRepository roomRepositoryRef;

	public Booking getBookingById(Long bookingId) {
		Booking booking = bookingRepoRef.findById(bookingId)
				.orElseThrow(() -> new CustomException("No booking avalable for id:" + bookingId));
		return booking;
	}

	public List<Booking> getBookingByUserId(Long userId) {
		List<Booking> bookings = bookingRepoRef.findByUserRefUserId(userId);
		if (bookings.isEmpty()) {
			throw new CustomException("No booking available for given user id");
		} else {
			return bookings;
		}
	}

	public List<Booking> getBookingByUserEmail(String email) {

		User userRef = userServiceRef.GetByEmail(email);
		Long userId = userRef.getUserId();
		return getBookingByUserId(userId);

	}

	public String makeBooking(Booking booking, Long userId, Long roomId) {
		Room room = roomServiceRef.getOneRoom(roomId);
		User user = userServiceRef.getOneUser(userId);
		if (room.isAvailable() == true) {
			booking.setUserRef(user);
			booking.setRoomRef(room);
			booking.setBookingStatus(BookingStatus.CONFIRMED);
			bookingRepoRef.save(booking);
			room.setAvailable(false);
			roomRepositoryRef.save(room);
			return "Booking successfull";

		} else {
			throw new CustomException("room is already booked");
		}
	}

	public String cancalBooking(Long bookingId) {
		Booking bookingRef = getBookingById(bookingId);
		
		if (bookingRef == null) {
			throw new CustomException("No Booking available for booking Id:" + bookingId);
		} else {
			Room roomRef = bookingRef.getRoomRef();
			bookingRef.setBookingStatus(BookingStatus.CANCELLED);
			roomRef.setAvailable(true);
			roomRepositoryRef.save(roomRef);
			return "Booking cancel suucessfully";

		}
	}

}

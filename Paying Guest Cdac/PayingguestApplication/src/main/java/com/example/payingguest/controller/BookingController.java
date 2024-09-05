package com.example.payingguest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payingguest.entities.Booking;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.exception.UserNotFoundException;
import com.example.payingguest.service.BookingService;
import com.example.payingguest.service.UserService;

@RestController
@RequestMapping("/pg_api/booking")
public class BookingController {

	@Autowired
	private BookingService bookingServiceRef;
	
	@Autowired
	private UserService userServiceRef;
	
	
	@GetMapping("/getBookingById/{bookingId}")
	public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {
		try {
		  return  new ResponseEntity<>(bookingServiceRef.getBookingById(bookingId),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/getBookingByUserId/{userId}")
	public ResponseEntity<?> getBookingByUserId(@PathVariable Long userId) {
		try {
		  return  new ResponseEntity<>(bookingServiceRef.getBookingByUserId(userId),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getBookingByUserEmail")
	public ResponseEntity<?> getBookingByUserEmail(@RequestHeader("Authorization") String jwt) {
		try {
			User userRef=userServiceRef.findUserProfileByJwt(jwt);
		  return  new ResponseEntity<>(bookingServiceRef.getBookingByUserEmail(userRef.getEmail()),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/book/{roomId}")
	public ResponseEntity<?> makeBooking(@RequestBody Booking booking, @PathVariable Long roomId,@RequestHeader("Authorization") String jwt) {
		try {
			User userRef=userServiceRef.findUserProfileByJwt(jwt);
			return new ResponseEntity<>(bookingServiceRef.makeBooking(booking, userRef.getUserId(), roomId),HttpStatus.OK);
		}catch (CustomException e) {
		    return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@DeleteMapping("/cancalBooking/{bookingId}")
	public ResponseEntity<?> cancalBooking(@PathVariable Long bookingId) {
		try {
			return new ResponseEntity<>(bookingServiceRef.cancalBooking(bookingId),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	
	
	
	
	
	
	
}

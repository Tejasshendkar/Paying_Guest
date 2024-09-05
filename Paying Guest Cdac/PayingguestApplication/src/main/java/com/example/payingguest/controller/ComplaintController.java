package com.example.payingguest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payingguest.entities.Complaint;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.exception.UserNotFoundException;
import com.example.payingguest.service.ComplaintService;
import com.example.payingguest.service.UserService;

@RestController
@RequestMapping("pg_api/complaint")
@CrossOrigin("*")
public class ComplaintController {
	@Autowired
	private ComplaintService complaintServiceRef;

	@Autowired
	private UserService userServiceRef;

	@PostMapping("/create")
	public ResponseEntity<?> writeComplaint(@RequestBody Complaint complaintRef,
			@RequestHeader("Authentication") String token) {
		try {
			User user = userServiceRef.findUserProfileByJwt(token);
			return complaintServiceRef.writeComplaint(complaintRef, user);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getComplaint(@PathVariable Long id) {
		return complaintServiceRef.getComplaint(id);
	}

//	@GetMapping("/getComplaintByUser/{id}")
//	public ResponseEntity<?> getComplaintByUserId(@PathVariable Long id){
//		try {
//		return  new ResponseEntity<>(complaintServiceRef.getComplaintByUserId(id),HttpStatus.OK);
//		}catch (ComplaintNotFoundException e) {
//		 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//		}
//	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		return complaintServiceRef.deleteComplaint(id);
	}
}

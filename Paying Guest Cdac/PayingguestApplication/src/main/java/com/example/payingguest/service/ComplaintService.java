package com.example.payingguest.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.payingguest.entities.Complaint;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.ComplaintNotFoundException;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.repository.ComplaintRepo;

@Service
public class ComplaintService {
	@Autowired
	private ComplaintRepo complaintRepoRef;

	public ResponseEntity<?> writeComplaint(Complaint complaintRef, User user) {
		
			complaintRef.setUserRef(user);
			complaintRef.setComplaintDate(LocalDate.now());
			Complaint complaint = complaintRepoRef.save(complaintRef);
			if (complaint != null)
				return new ResponseEntity<>(complaint, HttpStatus.OK);
			else
				throw new CustomException("unable to send complaint");
		
	}

	public ResponseEntity<?> getComplaint(Long id) {
		try {
			Optional<Complaint> complaint = complaintRepoRef.findById(id);
			if (complaint.isPresent())
				return new ResponseEntity<>(complaint, HttpStatus.OK);
			else
				throw new ComplaintNotFoundException("No Complaint");

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

//	public List<Complaint> getComplaintByUserId(Long userId) {
//		List<Complaint> complaint = complaintRepoRef.findByUserRef(userId);
//		if (complaint != null)
//			return complaint;
//		else {
//			throw new ComplaintNotFoundException("No Complaint");
//		}
//
//	}

	public String deleteComplaint(Long id) {
		try {
			Complaint complaint = complaintRepoRef.findById(id)
					.orElseThrow(() -> new ComplaintNotFoundException("No Complaint"));
			complaintRepoRef.delete(complaint);
			return "Complaint Deleted";

		} catch (Exception e) {
			return e.getMessage();
		}
	}

}

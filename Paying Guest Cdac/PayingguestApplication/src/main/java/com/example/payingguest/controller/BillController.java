package com.example.payingguest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payingguest.entities.Bill;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.service.BillService;
import com.example.payingguest.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/pg_api/bill")
public class BillController {

	@Autowired
	private BillService billServiceRef;
	
	@Autowired 
	private UserService userServiceRef;
	

	@PostMapping("/generateBill")
	public ResponseEntity<?> generateBill(@RequestHeader("Authorization") String jwt) {
		try {
			User userref=userServiceRef.findUserProfileByJwt(jwt);
			return new ResponseEntity<>(billServiceRef.generateBillForUser(userref), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}     
	}

	@GetMapping("/getbillbyid/{billId}")
	public ResponseEntity<?> getBill(@PathVariable Long billId) {
		try {
			return new ResponseEntity<>(billServiceRef.getBillById(billId), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getbill")
	public ResponseEntity<?>getBillByToken(@RequestHeader("Authorization") String token){
		try {
			User user=userServiceRef.findUserProfileByJwt(token);
			return new ResponseEntity<>(billServiceRef.getBillByUserId(user.getUserId()),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getbillbyUserid/{userId}")
	public ResponseEntity<?> getBillByUserId(@PathVariable Long userId) {
		try {
			return new ResponseEntity<>(billServiceRef.getBillByUserId(userId), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deletebill/{billId}")
	public ResponseEntity<?> deleteBill(@PathVariable Long billId) {
		try {
			return new ResponseEntity<>(billServiceRef.deleteBillbyBillId(billId), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/downloadBill/{billId}")
	public ResponseEntity<?> downloadPdf(@PathVariable Long billId) {
	    try {
	        Bill bill = billServiceRef.getBillById(billId);
	        byte[] pdfBytes = billServiceRef.generatePdf(bill);

	        // Check if the PDF byte array is not empty
	        if (pdfBytes != null && pdfBytes.length > 0) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_PDF);
	            headers.setContentDispositionFormData("attachment", billId + "_bill.pdf");
	            headers.setContentLength(pdfBytes.length);

	            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	        } else {
	            throw new CustomException("No pending bills or PDF generation failed");
	        }

	    } catch (CustomException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	       e.printStackTrace();
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


}

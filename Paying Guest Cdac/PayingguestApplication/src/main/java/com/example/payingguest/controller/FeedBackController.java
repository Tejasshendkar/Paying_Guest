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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payingguest.entities.FeedBack;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.service.FeedBackService;


@RestController
@RequestMapping("pg_api/feedback")
@CrossOrigin("*")
public class FeedBackController {
   
	@Autowired
	private FeedBackService feedBackServiceRef;
	
	
	@PostMapping("/write/{userid}/{pgid}")
	public ResponseEntity<?> writeFeedBack(@RequestBody FeedBack feedBackRef,@PathVariable Long userid,@PathVariable Long pgid){
		try {
			return new ResponseEntity<>(feedBackServiceRef.writeFeedBack(feedBackRef, userid, pgid),HttpStatus.CREATED);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getAllFeedback(){
		try {
			return new ResponseEntity<>(feedBackServiceRef.getAllFeedback(),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFeedBack(@PathVariable Long id){
		try {
			return new ResponseEntity<>(feedBackServiceRef.deleteFeedBack(id),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
}



















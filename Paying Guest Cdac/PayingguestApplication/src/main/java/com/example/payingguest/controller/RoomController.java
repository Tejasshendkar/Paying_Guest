package com.example.payingguest.controller;

import java.util.List;

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

import com.example.payingguest.entities.Room;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.exception.RoomNotFoundExceptions;
import com.example.payingguest.service.RoomService;
import com.example.payingguest.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/pg_api/room")
public class RoomController {

	@Autowired
	private RoomService roomServiceRef;
	
	@Autowired 
	private UserService userServiceRef;

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getroom(@PathVariable Long id) {
		try {
			Room room = roomServiceRef.getOneRoom(id);
			return new ResponseEntity<>(room, HttpStatus.OK);
		} catch (RoomNotFoundExceptions e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getroombypgid/{pgid}")
	public ResponseEntity<?> getRoomByPgId(@PathVariable Long pgid){
		try {
			return new ResponseEntity<>(roomServiceRef.getRoomsByPgId(pgid),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch (RoomNotFoundExceptions e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getallRoom(){
		try {
			return new ResponseEntity<>(roomServiceRef.getAllRoom(),HttpStatus.OK);
		}catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/register/{pgid}")
	public ResponseEntity<?> registerRoom(@RequestBody Room roomRef, @PathVariable Long pgid) {
		return roomServiceRef.registerRoom(roomRef, pgid);
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		return roomServiceRef.deleteRoom(id);
	}
	
	@PostMapping("/updateRoom/{roomId}")
	public ResponseEntity<?> updateRoom(@RequestBody Room room,@PathVariable Long roomId){
		try {
			return new ResponseEntity<>(roomServiceRef.updateRoom(room, roomId),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllRoomByUserId")
	public ResponseEntity<?>  getAllRoomByUserId(@RequestHeader("Authorization") String jwt){
		try {
			User user=userServiceRef.findUserProfileByJwt(jwt);
			System.out.println(user.getUserId());
		List<Room> roomList=	roomServiceRef.getAllRoomByUserWhoRegisertPG(user.getUserId());
			return new ResponseEntity<>(roomList,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
}







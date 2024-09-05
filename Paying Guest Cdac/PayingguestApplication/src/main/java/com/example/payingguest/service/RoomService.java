package com.example.payingguest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.payingguest.entities.PG;
import com.example.payingguest.entities.Room;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.exception.RoomNotFoundExceptions;
import com.example.payingguest.repository.PgRepo;
import com.example.payingguest.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepositoryRef;

	@Autowired
	private PgService pgServiceRef;
	
	@Autowired
	private PgRepo pgReporef;

//	public ResponseEntity<?> getRoom(Long id) {
//		try {
//			Room room = roomRepositoryRef.findById(id).orElseThrow(() -> new RoomNotFoundExceptions("Room Not Found"));
//			return new ResponseEntity<>(room, HttpStatus.OK);
//		} catch (RoomNotFoundExceptions e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	}

	public Room getOneRoom(Long id) {
		return roomRepositoryRef.findById(id).orElseThrow(() -> new RoomNotFoundExceptions("Room Not Found"));
	}
	
	
	@SuppressWarnings("unused")
	public List<Room> getRoomsByPgId(Long pgId){
		PG pg=pgServiceRef.getOnePg(pgId);
		List<Room> rooms=roomRepositoryRef.findByPgRefPgId(pgId);
		if(rooms.isEmpty()) {
		throw new RoomNotFoundExceptions("no room are available in this pg");
		}else {
			return rooms;
		}
	}

	public ResponseEntity<?> registerRoom(Room roomRef, Long pgid) {
		try {
			PG pgReg = pgServiceRef.getOnePg(pgid);
			roomRef.setPgRef(pgReg);
			roomRef.setAvailable(true);
			Room room = roomRepositoryRef.save(roomRef);
			if (room != null)
				return new ResponseEntity<>(room, HttpStatus.OK);
			else
				throw new CustomException("unable to register");
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	

	public String deleteRoom(Long id) {
		try {

			Room room = roomRepositoryRef.findById(id).orElseThrow(() -> new RoomNotFoundExceptions("Room Not Found"));
			if (room != null) {
				roomRepositoryRef.deleteById(id);
				return "Room deleted";
			} else {
				throw new CustomException("Unable to delete");
			}
		} catch (CustomException e) {
			return e.getMessage();
		} catch (RoomNotFoundExceptions e) {
			return e.getMessage();
		}
	}
	
	
	public List<Room> getAllRoom(){
		List<Room> rooms=roomRepositoryRef.findAllByIsAvailable(true);
		if(rooms.isEmpty()) {
			throw new CustomException("Rooms are not available");
		}else {
			return rooms;
		}
	}
	
	public Room updateRoom(Room roomRef,Long roomId) {
		Room  oldRoom=getOneRoom(roomId);
		if(oldRoom!=null) {
			oldRoom.setType(roomRef.getType());
			oldRoom.setCapacity(roomRef.getCapacity());
			oldRoom.setFloorNumber(roomRef.getFloorNumber());
			oldRoom.setAvailable(roomRef.isAvailable());
			oldRoom.setRent(roomRef.getRent());
			oldRoom.setRoomNumber(roomRef.getRoomNumber());
			oldRoom.setArea(roomRef.getArea());
		 return roomRepositoryRef.save(oldRoom);
		}
		else {
			throw new CustomException("room not found of id:"+roomId);
		}
	}
	
	public List<Room> getAllRoomByUserWhoRegisertPG(Long userId){
		List<PG> pgs=pgReporef.findByUserRefUserId(userId);
		List<Room> roomList= new ArrayList<>();
		System.out.println("length->"+pgs.size());
		if(!pgs.isEmpty()) {
			
			for(PG pg:pgs) {
					System.out.println(pg);
					List<Room> pgRoom=roomRepositoryRef.findByPgRefPgId(pg.getPgId());
					if(!pgRoom.isEmpty())
					roomList.addAll(pgRoom);
				}
			return roomList;
			}else {
				throw new CustomException("No Rooms available ");
			}
		   
		}
	
	
	}
	
	




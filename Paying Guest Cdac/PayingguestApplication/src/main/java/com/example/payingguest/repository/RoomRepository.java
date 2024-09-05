package com.example.payingguest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	List<Room> findByPgRefPgId(Long pgId);
    List<Room>	findAllByIsAvailable(boolean isAvailable );

}

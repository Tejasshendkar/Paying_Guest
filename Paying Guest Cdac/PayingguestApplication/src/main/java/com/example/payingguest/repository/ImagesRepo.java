package com.example.payingguest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.Images;

public interface ImagesRepo extends JpaRepository<Images, Long> {
	List<Images> findByRoomRefRoomId(Long roomId);
	List<Images> findByPgRefPgId(Long pgId);
}

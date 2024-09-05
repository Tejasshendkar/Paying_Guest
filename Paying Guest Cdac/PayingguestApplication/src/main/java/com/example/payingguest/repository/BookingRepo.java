package com.example.payingguest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {
       List<Booking> findByUserRefUserId(Long userId);
       
}

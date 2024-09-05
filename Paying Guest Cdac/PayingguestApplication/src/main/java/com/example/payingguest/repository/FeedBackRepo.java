package com.example.payingguest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payingguest.entities.FeedBack;

public interface FeedBackRepo extends JpaRepository<FeedBack, Long> {

}

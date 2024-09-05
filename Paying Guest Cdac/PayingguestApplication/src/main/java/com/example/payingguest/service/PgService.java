package com.example.payingguest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.payingguest.entities.PG;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.repository.PgRepo;

@Service
public class PgService {

	@Autowired
	private PgRepo pgRepoRef;
	
	@Autowired
	private UserService userServiceRef;

	public PG registerPg(PG pgRef,String jwtToken) {
			User userref=userServiceRef.findUserProfileByJwt(jwtToken);
			pgRef.setUserRef(userref);
			PG pg = pgRepoRef.save(pgRef);
		if(pg!=null) {
			return pg;
		}else {
			throw new CustomException("unable to register");
		}
		
	}

	public List<PG> getAllPgBYUser(String jwtToken) {
		User userref=userServiceRef.findUserProfileByJwt(jwtToken);
		List<PG> pgs=pgRepoRef.findByUserRefUserId(userref.getUserId());
		if(pgs.size()>0) {
			return pgs;
		}else {
			throw new CustomException("No pgs available of user have email:"+userref.getEmail());
		}
	}
	
	public List<PG> getAllPg() {
		List<PG> pgs=pgRepoRef.findAll();
		if(pgs.size()>0) {
			return pgs;
		}else {
			throw new CustomException("No pgs available");
		}
	}
	
	public PG  getOnePg(Long id) {
			return pgRepoRef.findById(id).orElseThrow(()->new CustomException("pg not Found"));
	}

	public ResponseEntity<?> getPgByLocation(String address) {
		try {
			List<PG> pgList = pgRepoRef.findByAddress(address);
			if (pgList.size() > 0) {
				return new ResponseEntity<>(pgList, HttpStatus.OK);
			} else {
				throw new CustomException("no pg found");
			}
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> update(PG newpg) {
		try {
			PG pg = pgRepoRef.findById(newpg.getPgId()).orElseThrow(() -> new CustomException("PG not found"));
			if (pg != null) {
				pg.setAddress(newpg.getAddress());
				pg.setPgName(newpg.getPgName());
				pg.setContactNo(newpg.getContactNo());
				pgRepoRef.save(pg);
				return new ResponseEntity<>("info updated Successful", HttpStatus.OK);
			} else {
				throw new CustomException("Unable to update");
			}
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<?> delete(Long id) {
		try {
			PG pg = pgRepoRef.findById(id).orElseThrow(() -> new CustomException("PG not found"));
			if (pg != null) {
				pgRepoRef.deleteById(id);
				return new ResponseEntity<>("pg deleted Successful", HttpStatus.OK);
			} else {
				throw new CustomException("Unable to delete");
			}
		} catch (CustomException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}

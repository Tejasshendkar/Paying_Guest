package com.example.payingguest.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.payingguest.entities.Images;
import com.example.payingguest.entities.PG;
import com.example.payingguest.entities.Room;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.exception.ImageNotFoundException;
import com.example.payingguest.repository.ImagesRepo;

@Service
public class ImagesService {
	@Autowired
	private ImagesRepo imagesRepoRef;
	@Autowired
	private RoomService roomServiceRef;
	
	@Autowired
	private PgService pgServiceRef;

	public List<Images> saveImage(MultipartFile[] imagefile, Long roomId) throws IOException {
		System.out.println(" service image");
		Set<Images> images = new HashSet<>();
		for (MultipartFile img : imagefile) {
			Images image = new Images();
			image.setImagedata(img.getBytes());
			Room roomRef = roomServiceRef.getOneRoom(roomId);
			image.setRoomRef(roomRef);
			images.add(image);			
		}
		List<Images> image1 = imagesRepoRef.saveAll(images);
		if (image1 != null) {
			return image1;
		} else {
			throw new CustomException("unable to save image try again latter");
		}

	}
	
	
	public List<Images> saveImageForPg(MultipartFile[] imagefile, Long pgId) throws IOException {
		System.out.println(" service image");
		Set<Images> images = new HashSet<>();
		for (MultipartFile img : imagefile) {
			Images image = new Images();
			image.setImagedata(img.getBytes());
			PG pgRef = pgServiceRef.getOnePg(pgId);
			image.setPgRef(pgRef);
			images.add(image);			
		}
		List<Images> image1 = imagesRepoRef.saveAll(images);
		if (image1 != null) {
			return image1;
		} else {
			throw new CustomException("unable to save image try again latter");
		}

	}

	public List<Images> getImagesDataByRoomId(Long roomId) {
		List<Images> images = imagesRepoRef.findByRoomRefRoomId(roomId);
		if (images.isEmpty()) {
			throw new ImageNotFoundException("No images found for room ID: " + roomId);
		}
		return images;
	}
	
	
	public List<Images> getImagesDataByPgId(Long pgId) {
		List<Images> images = imagesRepoRef.findByPgRefPgId(pgId);
		if (images.isEmpty()) {
			throw new ImageNotFoundException("No images found for pg ID: " + pgId);
		}
		return images;
	}

	public ResponseEntity<?> delete(Long id) {

		Optional<Images> img = imagesRepoRef.findById(id);
		if (img.isPresent()) {
			imagesRepoRef.deleteById(id);
			return new ResponseEntity<>("image deleted successful", HttpStatus.OK);
		} else {
			throw new ImageNotFoundException("image Not found");
		}
	}

	
}

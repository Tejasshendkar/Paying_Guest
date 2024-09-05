package com.example.payingguest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payingguest.entities.FeedBack;
import com.example.payingguest.entities.PG;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.repository.FeedBackRepo;

@Service
public class FeedBackService {

	@Autowired
	private FeedBackRepo feedBackRepoRef;

	@Autowired
	private UserService userServiceRef;

	@Autowired
	private PgService pgServiceRef;

	public FeedBack writeFeedBack(FeedBack feedBackRef, Long userid, Long pgid) {
		User user = userServiceRef.getOneUser(userid);
		PG pgRef = pgServiceRef.getOnePg(pgid);
		feedBackRef.setUserRef(user);
		feedBackRef.setPgRef(pgRef);
		FeedBack feedBack = feedBackRepoRef.save(feedBackRef);
		if (feedBack != null) {
			return feedBack;
		} else {
			throw new CustomException("unable to save feedBack");
		}
	}

//	public FeedBack getFeedBackByUserId(Long userid) {
//		
//	}

	public List<FeedBack> getAllFeedback() {
		List<FeedBack> feedbackList = feedBackRepoRef.findAll();
		if (feedbackList.isEmpty()) {
			throw new CustomException("No feedback available");
		} else {
			return feedbackList;
		}
	}

	public String deleteFeedBack(Long id) {
		FeedBack feedBack = feedBackRepoRef.findById(id).orElseThrow(() -> new CustomException("Feedback not found"));
		if (feedBack != null) {
			feedBackRepoRef.deleteById(id);
			return "feedback deleted successfull";
		} else {
			throw new CustomException("unable to delete feedback");
		}
	}
}

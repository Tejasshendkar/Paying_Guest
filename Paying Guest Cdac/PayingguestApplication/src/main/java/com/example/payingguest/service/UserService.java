package com.example.payingguest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.payingguest.config.JwtProvider;
import com.example.payingguest.entities.User;
import com.example.payingguest.exception.CustomException;
import com.example.payingguest.exception.EmailAlreadyExistsException;
import com.example.payingguest.exception.UserNotFoundException;
import com.example.payingguest.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepoRef;

	@Autowired
	private JwtProvider jwtProviderRef;
	
	@Autowired
	private PasswordEncoder encoder;

	public List<User> GetAllUsers() {
		List<User> userList = userRepoRef.findAll();
		if (userList.size() != 0)
			return userList;
		else {
			throw new UserNotFoundException("User Not Found");
		}
	}

	public User getOneUser(Long id) {
		return userRepoRef.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
	}

	public List<User> GetAllUsersByRole(String role) {
		List<User> userList = userRepoRef.findByRole(role);
		if (userList.size() != 0)
			return userList;
		else {
			throw new UserNotFoundException("User Not Found");
		}
	}

	public User GetByEmail(String mail) {
		User userRef = userRepoRef.findByEmail(mail);
		if (userRef != null) {
			return userRef;
		} else {

			throw new UserNotFoundException("User Not Found");

		}
	}

	public User registration(User newUser) {
		User user = userRepoRef.findByEmail(newUser.getEmail());
		if (user == null) {
			return userRepoRef.save(newUser);
		} else {
			throw new EmailAlreadyExistsException("An account with this email already exists.");
		}
	}

	public ResponseEntity<?> delete(Long id) {
		User user = userRepoRef.findById(id).orElseThrow(() -> new UserNotFoundException("user Not Found"));
		if (user != null) {
			userRepoRef.delete(user);
			return new ResponseEntity<>("user deleted", HttpStatus.OK);
		} else {
			throw new CustomException("unable to delete");
		}

	}

	public ResponseEntity<?> update(User userRef) {
		User user = userRepoRef.findById(userRef.getUserId())
				.orElseThrow(() -> new UserNotFoundException("user Not Found to Update"));
		if (user != null) {
			user.setAddress(userRef.getAddress());
			user.setEmail(userRef.getEmail());
			user.setMobileNo(userRef.getMobileNo());
			user.setName(userRef.getName());
			userRepoRef.save(user);
			return new ResponseEntity<>("info Updated", HttpStatus.OK);
		} else {
			throw new CustomException("unable to updaate");
		}
	}

	public ResponseEntity<?> upadatePassword(String email, String oldPassword,String newPassword) {
		User user = userRepoRef.findByEmail(email);
		System.out.println(email+","+oldPassword+","+newPassword);
		if (user != null) {
			String	oldPass=user.getPassword();
			
			if (!encoder.matches(oldPass, user.getPassword())) {
				user.setPassword(encoder.encode(newPassword));
			userRepoRef.save(user);
			return new ResponseEntity<>("Password change successuful", HttpStatus.OK);
			}else {
				throw new CustomException("unable to update password");
			}
		} else {
			throw new CustomException("unable to change password");
		}
	}

	// to disable spring generated password by spring
	// User can use his custom password
	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
		User user = userRepoRef.findByEmail(username);
		if (user == null) {
			throw new UserNotFoundException("User not found with email: " + username);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	public User findUserProfileByJwt(String jwt) throws UserNotFoundException {
		String email = jwtProviderRef.getEmailFromToken(jwt);
		User user = userRepoRef.findByEmail(email);
		if (user == null) {
			throw new UserNotFoundException("User not found with this email: " + email);
		}
		return user;
	}

}

package com.aditya.mobilews.ui.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.mobilews.ui.model.request.UpdateUserDetailsRequestModel;
import com.aditya.mobilews.ui.model.request.UserDetailsRequestModel;
import com.aditya.mobilews.ui.model.response.UserRest;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

	Map<String, UserRest> userMap;
	
	@GetMapping // http://localhost:8080/users?page=1&limit=50
	public String getUser(@RequestParam(value = "page", defaultValue = "1") int page, 
				@RequestParam(value = "limit", defaultValue = "50") int limit,
				@RequestParam(value = "sort", defaultValue = "desc", required = false) String sort) {
		return "get user called with page = " + page + " and limit = " + limit + " with sort as " + sort;
	}

	@GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_XML, 
												MediaType.APPLICATION_JSON}) // http://localhost:8080/users/sisei8829lslkkd
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		
		if (userMap.containsKey(userId))
			return new ResponseEntity<UserRest> (userMap.get(userId), HttpStatus.OK);
		else 
			return new ResponseEntity<UserRest> (HttpStatus.NO_CONTENT);	
	}

	@PostMapping(consumes = {MediaType.APPLICATION_XML, 
							MediaType.APPLICATION_JSON},
				produces = {MediaType.APPLICATION_XML, 
							MediaType.APPLICATION_JSON})
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnUser = new UserRest();
		returnUser.setFirstName(userDetails.getFirstName());
		returnUser.setLastName(userDetails.getLastName());
		returnUser.setEmail(userDetails.getEmail());

		String userId = UUID.randomUUID().toString();
		returnUser.setUserId(userId);
		
		if (userMap==null) userMap = new HashMap<>();
		userMap.put(userId, returnUser);
		return new ResponseEntity<UserRest> (returnUser, HttpStatus.OK);
	}

	
	@PutMapping(path="/{userId}", consumes = {MediaType.APPLICATION_XML, 
							MediaType.APPLICATION_JSON},
				produces = {MediaType.APPLICATION_XML, 
							MediaType.APPLICATION_JSON})
	public UserRest updateUser(@PathVariable String userId,@Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
		if (userMap.containsKey(userId)) {
			UserRest storedUser = userMap.get(userId);

			storedUser.setFirstName(userDetails.getFirstName());
			storedUser.setLastName(userDetails.getLastName());
			
			userMap.put(userId, storedUser);
			
			return storedUser;
			
		}
		else {
			return null;
		}
	}

	@DeleteMapping(path="/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		
		if (userMap.containsKey(userId)) {
			userMap.remove(userId);
		}
		
		return ResponseEntity.noContent().build();
	}
}

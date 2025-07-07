package com.blogset.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogset.blog.entities.User;
import com.blogset.blog.exceptions.ResourceNotFoundException;
import com.blogset.blog.payloads.ApiResponce;
import com.blogset.blog.payloads.UserDto;
import com.blogset.blog.repositories.UserRepo;
import com.blogset.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	// post create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
		
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
//	ADMIN
	@DeleteMapping("/api/users/{userId}")
	@PreAuthorize("hasRole('ADMIN_USER')")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
	    User user = userRepo.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
	    
	    userRepo.delete(user); // ✅ Make sure you're deleting the user, not their roles

	    return ResponseEntity.ok("User deleted successfully.");
	}

	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
		
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
		
	}
	
	@GetMapping("/api/test-role")
	@PreAuthorize("hasRole('ADMIN_USER')")
	public ResponseEntity<String> testRole() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("Authorities: " + auth.getAuthorities());
	    return ResponseEntity.ok("You are ADMIN");
	}

}

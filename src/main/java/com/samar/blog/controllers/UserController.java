package com.samar.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samar.blog.payloads.ApiResponse;
import com.samar.blog.payloads.UserDto;
import com.samar.blog.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getUser(){
		List<UserDto> foundUser = this.userService.getAllUsers();
		return new ResponseEntity<>(foundUser,HttpStatus.FOUND);
	}
	
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createdUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
		
	}
      
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid){
		UserDto updateUserDto = this.userService.updateUser(userDto, uid);
		
		
	
		return new ResponseEntity<>(updateUserDto,HttpStatus.CREATED);
	}
	
	
//	@DeleteMapping("/users/{userId}")
//	public ResponseEntity<?> deleteUser(@RequestBody UserDto userdto,@PathVariable("userId") Integer uid){
//		this.userService.deleteUser(uid);
//		
//		return new ResponseEntity<>(Map.of("message","user deleted successfully"),HttpStatus.OK);
//		
//	}
		

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@RequestBody UserDto userdto,@PathVariable("userId") Integer uid){
		this.userService.deleteUser(uid);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully , yes !!",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer uid){
		return new ResponseEntity<>(this.userService.getUserById(uid),HttpStatus.FOUND);
	}
}

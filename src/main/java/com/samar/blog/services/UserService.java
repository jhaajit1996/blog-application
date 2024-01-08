package com.samar.blog.services;

import java.util.List;

import com.samar.blog.entities.User;
import com.samar.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
	

}

package com.samar.blog.services.impl;


import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samar.blog.config.AppConstants;
import com.samar.blog.entities.Role;
import com.samar.blog.entities.User;
import com.samar.blog.payloads.UserDto;
import com.samar.blog.repositories.RoleRepo;
import com.samar.blog.repositories.UserRepo;
import com.samar.blog.services.UserService;

import com.samar.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		
		
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        
        User savedUser = this.userRepo.save(user);
        UserDto SavedUserDto = this.userToDto(savedUser);
        return userDto;
        
        
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		
		return this.userToDto(user);
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDto = users.stream().map((user)->this.userToDto(user)).collect(Collectors.toList());
		
		return userDto;
		
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		
		this.userRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		
		
		User user  = modelMapper.map(userDto, User.class);
		
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		
		return user;
		
	}
	
	private UserDto userToDto(User user) {
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User registeredUser = userRepo.save(user);
		
		return this.modelMapper.map(registeredUser,UserDto.class);
		
		
	}

}

package com.samar.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@NotNull
	@Size(min=4,message = "username must be greater than 4 character !!")
	private String name;
	
	@NotEmpty
	@NotNull
	@Email
	private String email;
	
	@NotEmpty
	@NotNull
	@Size(min=4,max=10,message = "password must be between size of 4 characters and 10 characters !!")
	private String password;
	
	@NotEmpty
	@NotNull
	@Size(min=4,message="about must be greater than 4 characters !!")
	private String about;

}

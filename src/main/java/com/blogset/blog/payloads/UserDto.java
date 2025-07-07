package com.blogset.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blogset.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
	@Size(min = 4, message = "Username mmust be minimum of 4 characters")
	private String name;
	
	@Email(message = "Email address is not valid !!")
	@NotEmpty(message = "Email is required !!")
	private String email;
	
	@NotBlank
	@Size(min = 3, max = 10, message = "Password must be minimum of 3 char and maximumm of 10 char")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	
	
}

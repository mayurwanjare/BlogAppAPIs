package com.blogset.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
import com.blogset.blog.entities.User;
import com.blogset.blog.exceptions.ResourceNotFoundException;
import com.blogset.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		loading userfrom database by username
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(()-> new ResourceNotFoundException("User", "email : "+username, 0));
		
		return user;
	}

}

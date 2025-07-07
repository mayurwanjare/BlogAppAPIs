package com.blogset.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogset.blog.config.AppConstants;
import com.blogset.blog.entities.Role;
import com.blogset.blog.repositories.RoleRepo;

@SpringBootApplication
public class DemoblogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(DemoblogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper () {
		
		return new ModelMapper();
	} 

	@Override
	public void run(String... args) throws Exception {
	    System.out.println(this.passwordEncoder.encode("dcba"));
	    
	    try {
	        if (roleRepo.count() == 0) { // Only insert if no roles exist
	            Role adminRole = new Role();
	            adminRole.setId(AppConstants.ADMIN_USER); // e.g., 501
	            adminRole.setName("ADMIN_USER");

	            Role normalRole = new Role();
	            normalRole.setId(AppConstants.NORMAL_USER); // e.g., 502
	            normalRole.setName("NORMAL_USER");

	            List<Role> roles = List.of(adminRole, normalRole);
	            List<Role> saved = this.roleRepo.saveAll(roles);

	            saved.forEach(r -> System.out.println("Inserted role: " + r.getName()));
	        } else {
	            System.out.println("Roles already exist in database.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error seeding roles: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


}

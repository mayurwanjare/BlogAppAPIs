package com.blogset.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogset.blog.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String Email);

}

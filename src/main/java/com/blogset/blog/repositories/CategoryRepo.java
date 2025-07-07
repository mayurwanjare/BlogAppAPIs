package com.blogset.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogset.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

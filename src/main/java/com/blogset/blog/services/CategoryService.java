package com.blogset.blog.services;

import java.util.List;

import com.blogset.blog.payloads.CategoryDto;

public interface CategoryService {
	
//	create 
	CategoryDto createCategory(CategoryDto categoryDto);
	
//	update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
//	delete
	public void deleteCategory(Integer categoryId);
	
//	get
	public CategoryDto getCategory(Integer categoryId);
	
//	getall
	List<CategoryDto> getCategories();

}

package com.samar.blog.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samar.blog.entities.Category;
import com.samar.blog.payloads.CategoryDto;

public interface CategoryService{
	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	void deleteCategory(Integer categoryId);
	CategoryDto getCategory(Integer categoryId);
	List<CategoryDto> getCategories();
	

}

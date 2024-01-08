package com.samar.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samar.blog.entities.Category;
import com.samar.blog.exceptions.ResourceNotFoundException;
import com.samar.blog.payloads.CategoryDto;
import com.samar.blog.repositories.CategoryRepo;
import com.samar.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory =  this.categoryRepo.save(category);
		return this.modelMapper.map(addedCategory, CategoryDto.class);		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		//understand this below code in detail
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryID",categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));	
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","categoryId",categoryId));
	    return this.modelMapper.map(category, CategoryDto.class);
		
		
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categoryList = this.categoryRepo.findAll();
		List<CategoryDto> finalCategoryList = categoryList.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return finalCategoryList;
	}

}

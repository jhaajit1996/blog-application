package com.samar.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samar.blog.payloads.ApiResponse;
import com.samar.blog.payloads.CategoryDto;
import com.samar.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// create

	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto Categorydto) {

		CategoryDto createdCategory = this.categoryService.createCategory(Categorydto);
		ResponseEntity<CategoryDto> responseEntity = new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
		return responseEntity;

	}

	// update

	@PutMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			                          @PathVariable("categoryId") Integer cid) {
		CategoryDto updatedCategory =  this.categoryService.updateCategory(categoryDto, cid);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	
	
	// delete
	
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cid) {
		this.categoryService.deleteCategory(cid);
		
		return new ResponseEntity<>(new ApiResponse("category deleted successfully , o yes !!",true),HttpStatus.OK);
		
		
	}

	// get
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getCategory(){
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getCategories(),HttpStatus.FOUND);
		
	}

	// get by id
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		return new ResponseEntity<CategoryDto>(this.categoryService.getCategory(categoryId),HttpStatus.FOUND);
		
	}

}

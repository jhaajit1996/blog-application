package com.samar.blog.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.samar.blog.entities.Post;
import com.samar.blog.payloads.ApiResponse;
import com.samar.blog.payloads.PostDto;
import com.samar.blog.payloads.PostResponse;

public interface PostService {

	
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	ResponseEntity<ApiResponse> deletePost(Integer postId);
	
    PostResponse getAllPost(Integer pageSize,Integer pageNumber,String sortBy,String sorting);
    
    PostDto getPostById(Integer postId);
    
    List<PostDto> getPostByCategory(Integer categoryId);
    
    List<PostDto> getPostByUser(Integer userID);
    
    List<PostDto> searchPost(String keyword);

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
}

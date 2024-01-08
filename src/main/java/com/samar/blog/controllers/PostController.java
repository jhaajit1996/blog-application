package com.samar.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samar.blog.payloads.ApiResponse;
import com.samar.blog.payloads.PostDto;
import com.samar.blog.payloads.PostResponse;
import com.samar.blog.services.FileService;
import com.samar.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/api/v1")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer uid,
			@PathVariable("categoryId") Integer cid) {

		PostDto createdPost = this.postService.createPost(postDto, uid, cid);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);

	}

	@PutMapping("/users/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto fetchedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(fetchedPostDto, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/users/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="5",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="postId",required=false)String sortBy,
			@RequestParam(value="sorting",defaultValue="asc",required=false)String sorting){
		PostResponse posts= this.postService.getAllPost(pageSize,pageNumber,sortBy,sorting);
		ResponseEntity<PostResponse> responseEntity = new ResponseEntity<PostResponse>(posts,HttpStatus.FOUND);
		return responseEntity;
	}

	
	@GetMapping("/users/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto post = this.postService.getPostById(postId);
		ResponseEntity<PostDto> result = new ResponseEntity<PostDto>(post,HttpStatus.FOUND);
		return result;
	}
	
	
	@GetMapping("/users/posts/categories/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByCategory(categoryId),HttpStatus.FOUND);
	}
	
	
	@GetMapping("/users/posts/posts/{postId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer postId){
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByCategory(postId),HttpStatus.FOUND);
	}
	
	
	@DeleteMapping("/users/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId){
		
		ResponseEntity<?> responseEntity = this.postService.deletePost(postId);
		return responseEntity;
		
		
	}
	
	@GetMapping("/users/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword){
		
		List<PostDto> postDtos = this.postService.searchPost(keyword);
		ResponseEntity<List<PostDto>> responseEntity = new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.FOUND);
		return responseEntity;
		
	}
	
	
	@PostMapping("/users/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException{
		
		
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		
		
		postDto.setImageName(fileName);
		
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.ACCEPTED);
		
	}
	
	
	@GetMapping(value="/users/posts/profiles/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
		
	}
}



package com.samar.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samar.blog.entities.Category;
import com.samar.blog.entities.Post;
import com.samar.blog.entities.User;
import com.samar.blog.exceptions.ResourceNotFoundException;
import com.samar.blog.payloads.ApiResponse;
import com.samar.blog.payloads.PostDto;
import com.samar.blog.payloads.PostResponse;
import com.samar.blog.repositories.CategoryRepo;
import com.samar.blog.repositories.PostRepo;
import com.samar.blog.repositories.UserRepo;
import com.samar.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		Post post = this.modelMapper.map(postDto, Post.class);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setCategory(category);
		post.setUser(user);

		Post savedPost = this.postRepo.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post posts = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		posts.setContent(postDto.getContent());
		posts.setTitle(postDto.getTitle());
		posts.setImageName(postDto.getImageName());
		posts.setAddedDate(new Date());

		Post savedPost = this.postRepo.save(posts);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public ResponseEntity<ApiResponse> deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		this.postRepo.delete(post);

		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfully !!", true), HttpStatus.OK);

	}

	@Override
	public PostResponse getAllPost(Integer pageSize,Integer pageNumber,String sortBy,String sorting) {
		
		
		Sort sort = null;
		
		if(sorting.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
		
		sort = Sort.by(sortBy).descending();
		
		}
		Pageable p = PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> posts = pagePost.getContent();

		List<PostDto> postToBeSentBack = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postToBeSentBack);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		return this.modelMapper.map(post, PostDto.class);

	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postToBeSent = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postToBeSent;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		List<Post> postByUser = this.postRepo.findByUser(user);
		List<PostDto> postsToBeSent = postByUser.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postsToBeSent;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> post = this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDto = post.stream().map((e)->this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}

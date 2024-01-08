package com.samar.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samar.blog.entities.Category;
import com.samar.blog.entities.Post;
import com.samar.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
	List<Post> findByContentContaining(String content);
	
}

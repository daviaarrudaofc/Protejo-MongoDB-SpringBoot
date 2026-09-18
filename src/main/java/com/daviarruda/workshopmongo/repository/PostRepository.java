package com.daviarruda.workshopmongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.daviarruda.workshopmongo.domain.Post;


public interface PostRepository extends MongoRepository<Post, String>{
	
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")//// opção alternativa de acordo com regex MongoDB
	List<Post> searchByTitle(String text);

}

package com.daviarruda.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.daviarruda.workshopmongo.domain.Post;


public interface PostRepository extends MongoRepository<Post, String>{

}
